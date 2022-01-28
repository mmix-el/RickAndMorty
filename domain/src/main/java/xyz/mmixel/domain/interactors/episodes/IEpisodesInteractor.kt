package xyz.mmixel.domain.interactors.episodes

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails

interface IEpisodesInteractor {
    fun getEpisodes(): LiveData<PagingData<EpisodeDetails>>
    fun getFilteredEpisodes(episodeQuery: EpisodeQuery): LiveData<PagingData<EpisodeDetails>>
    suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String): List<EpisodeDetails>
    suspend fun getEpisodeById(id: Int): EpisodeDetails?
}