package xyz.mmixel.rickandmorty.di

import android.content.Context
import xyz.mmixel.rickandmorty.di.modules.AppModule
import xyz.mmixel.rickandmorty.di.modules.CacheModule
import xyz.mmixel.rickandmorty.di.modules.NetworkModule
import xyz.mmixel.rickandmorty.ui.di.characters.CharactersFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.characters.details.CharacterDetailsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.episodes.EpisodesFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.episodes.details.EpisodeDetailsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.locations.LocationsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.locations.details.LocationDetailsFragmentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CacheModule::class, NetworkModule::class])
interface ApplicationComponent {
    val context: Context
    val charactersFragmentComponent: CharactersFragmentComponent
    val episodesFragmentComponent: EpisodesFragmentComponent
    val locationsFragmentComponent: LocationsFragmentComponent
    val characterDetailsFragmentComponent: CharacterDetailsFragmentComponent
    val episodeDetailsFragmentComponent: EpisodeDetailsFragmentComponent
    val locationDetailsFragmentComponent: LocationDetailsFragmentComponent
}