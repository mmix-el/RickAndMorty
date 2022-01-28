package xyz.mmixel.rickandmorty.ui.di.episodes

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.episodes.EpisodesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodesFragmentModule::class])
interface EpisodesFragmentComponent {
    fun inject(episodesFragment: EpisodesFragment)
}