package xyz.mmixel.domain.interactors.episodes

import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class EpisodesInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : IEpisodesInteractor {

    override fun getEpisodes() = repository.getEpisodes()

    override fun getFilteredEpisodes(episodeQuery: EpisodeQuery) =
        repository.queryEpisodes(episodeQuery)

    override suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String) =
        repository.getMultipleEpisodes(characterId, episodesIds)

    override suspend fun getEpisodeById(id: Int) = repository.getEpisode(id)
}