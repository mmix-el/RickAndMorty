package xyz.mmixel.rickandmorty.ui.di.episodes.details

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.episodes.EpisodeDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodeDetailsFragmentModule::class])
interface EpisodeDetailsFragmentComponent {
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}