package xyz.mmixel.data.paging.episodes

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.mmixel.data.cache.Cache
import xyz.mmixel.data.network.Network
import xyz.mmixel.domain.entities.db.CharacterEpisode
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.domain.utils.RegexUtil

class EpisodesPagingSource(
    private val cache: Cache,
    private val network: Network
) : PagingSource<Int, EpisodeDetails>() {

    override fun getRefreshKey(state: PagingState<Int, EpisodeDetails>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDetails> {
        var results: List<EpisodeDetails>

        val page: Int = params.key ?: FIRST_PAGE_INDEX
        val pageSize = params.loadSize
        var nextKey: Int? = null

        try {
            network.getEpisodes(page).apply {

                if (this.info.next != null) {
                    val uri = Uri.parse(this.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextKey = nextPageQuery?.toInt()
                }

                results = this.results

                results.forEach { episodeDetails ->

                    cache.insertEpisode(episodeDetails.toEpisodeEntity())

                    episodeDetails.characters.forEach { characterUrl ->

                        cache.insertCharacterEpisode(
                            CharacterEpisode(
                                RegexUtil.extractIdFromUrl(characterUrl),
                                episodeDetails.id
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            cache.getEpisodes().apply {
                nextKey = if (size < pageSize) null else nextKey?.plus(1)
                results = this.map { episodeEntity ->
                    EpisodeDetails(
                        episodeEntity.episodeId,
                        episodeEntity.name,
                        episodeEntity.airDate,
                        episodeEntity.episode,
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