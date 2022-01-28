package xyz.mmixel.rickandmorty.ui.di.episodes.details

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.details.EpisodeDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodeDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    fun bindEpisodeDetailsViewModel(episodeDetailsViewModel: EpisodeDetailsViewModel): ViewModel
}