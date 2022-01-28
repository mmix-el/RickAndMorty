package xyz.mmixel.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.interactors.episodes.EpisodesInteractor
import xyz.mmixel.rickandmorty.di.Injector
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    val episodesInteractor: EpisodesInteractor
) : ViewModel() {

    fun getEpisodes() = episodesInteractor.getEpisodes().cachedIn(viewModelScope)

    fun getFilteredEpisodes(episodeQuery: EpisodeQuery) =
        episodesInteractor.getFilteredEpisodes(episodeQuery).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodesFragmentComponent()
    }
}