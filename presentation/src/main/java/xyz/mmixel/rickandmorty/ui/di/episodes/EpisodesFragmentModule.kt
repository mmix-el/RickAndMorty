package xyz.mmixel.rickandmorty.ui.di.episodes

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.EpisodesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodesFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindEpisodesViewModel(episodesViewModel: EpisodesViewModel): ViewModel
}