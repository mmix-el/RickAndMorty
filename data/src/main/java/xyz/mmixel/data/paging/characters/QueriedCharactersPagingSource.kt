package xyz.mmixel.data.paging.characters

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.mmixel.data.cache.Cache
import xyz.mmixel.data.network.Network
import xyz.mmixel.domain.entities.db.CharacterEpisode
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.domain.entities.network.characters.Place
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.utils.RegexUtil

class QueriedCharactersPagingSource(
    private val characterQuery: CharacterQuery,
    private val cache: Cache,
    private val network: Network
) : PagingSource<Int, CharacterDetails>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDetails>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetails> {
        var results: List<CharacterDetails>

        val page: Int = params.key ?: FIRST_PAGE_INDEX
        val pageSize = params.loadSize
        var nextKey: Int? = null

        try {
            network.queryCharacters(characterQuery, page).apply {

                if (this.info.next != null) {
                    val uri = Uri.parse(this.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextKey = nextPageQuery?.toInt()
                }

                results = this.results

                results.forEach { characterDetails ->

                    cache.insertCharacter(characterDetails.toCharacterEntity())

                    characterDetails.episode.forEach { episodeUrl ->

                        cache.insertCharacterEpisode(
                            CharacterEpisode(
                                characterDetails.id,
                                RegexUtil.extractIdFromUrl(episodeUrl)
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            cache.queryCharacters(characterQuery).apply {
                nextKey = if (size < pageSize) null else nextKey?.plus(1)
                results = this.map { characterEntity ->
                    CharacterDetails(
                        characterEntity.characterId,
                        characterEntity.name,
                        characterEntity.status,
                        characterEntity.species,
                        characterEntity.type,
                        characterEntity.gender,
                        Place("", ""),
                        Place("", ""),
                        characterEntity.image,
                        emptyList()
                    )
                }
            }
        }

        return LoadResult.Page(data = results, prevKey = null, nextKey = nextKey)
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}