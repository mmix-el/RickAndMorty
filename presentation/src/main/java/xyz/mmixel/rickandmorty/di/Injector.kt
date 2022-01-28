package xyz.mmixel.rickandmorty.di

import android.content.Context
import xyz.mmixel.rickandmorty.di.modules.AppModule
import xyz.mmixel.rickandmorty.ui.di.characters.CharactersFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.characters.details.CharacterDetailsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.episodes.EpisodesFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.episodes.details.EpisodeDetailsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.locations.LocationsFragmentComponent
import xyz.mmixel.rickandmorty.ui.di.locations.details.LocationDetailsFragmentComponent

object Injector {
    private lateinit var applicationComponent: ApplicationComponent

    private var charactersFragmentComponent: CharactersFragmentComponent? = null
    private var episodesFragmentComponent: EpisodesFragmentComponent? = null
    private var locationsFragmentComponent: LocationsFragmentComponent? = null
    private var characterDetailsFragmentComponent: CharacterDetailsFragmentComponent? = null
    private var episodeDetailsFragmentComponent: EpisodeDetailsFragmentComponent? = null
    private var locationDetailsFragmentComponent: LocationDetailsFragmentComponent? = null

    fun init(context: Context) {
        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(context))
            .build()
    }

    fun plusCharactersFragmentComponent(): CharactersFragmentComponent? {
        if (charactersFragmentComponent == null) {
            charactersFragmentComponent = applicationComponent.charactersFragmentComponent
        }

        return charactersFragmentComponent
    }

    fun clearCharactersFragmentComponent() {
        charactersFragmentComponent = null
    }

    fun plusEpisodesFragmentComponent(): EpisodesFragmentComponent? {
        if (episodesFragmentComponent == null) {
            episodesFragmentComponent = applicationComponent.episodesFragmentComponent
        }

        return episodesFragmentComponent
    }

    fun clearEpisodesFragmentComponent() {
        episodesFragmentComponent = null
    }

    fun plusLocationsFragmentComponent(): LocationsFragmentComponent? {
        if (locationsFragmentComponent == null) {
            locationsFragmentComponent = applicationComponent.locationsFragmentComponent
        }

        return locationsFragmentComponent
    }

    fun clearLocationsFragmentComponent() {
        locationsFragmentComponent = null
    }

    fun plusCharacterDetailsFragmentComponent(): CharacterDetailsFragmentComponent? {
        if (characterDetailsFragmentComponent == null) {
            characterDetailsFragmentComponent =
                applicationComponent.characterDetailsFragmentComponent
        }

        return characterDetailsFragmentComponent
    }

    fun clearCharacterDetailsFragmentComponent() {
        characterDetailsFragmentComponent = null
    }

    fun plusEpisodeDetailsFragmentComponent(): EpisodeDetailsFragmentComponent? {
        if (episodeDetailsFragmentComponent == null) {
            episodeDetailsFragmentComponent = applicationComponent.episodeDetailsFragmentComponent
        }

        return episodeDetailsFragmentComponent
    }

    fun clearEpisodeDetailsFragmentComponent() {
        episodeDetailsFragmentComponent = null
    }

    fun plusLocationDetailsFragmentComponent(): LocationDetailsFragmentComponent? {
        if (locationDetailsFragmentComponent == null) {
            locationDetailsFragmentComponent = applicationComponent.locationDetailsFragmentComponent
        }

        return locationDetailsFragmentComponent
    }

    fun clearLocationDetailsFragmentComponent() {
        locationDetailsFragmentComponent = null
    }
}