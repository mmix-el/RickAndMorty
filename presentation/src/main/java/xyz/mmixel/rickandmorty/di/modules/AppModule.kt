package xyz.mmixel.rickandmorty.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import xyz.mmixel.data.repository.RickAndMortyRepository
import xyz.mmixel.domain.interactors.characters.CharactersInteractor
import xyz.mmixel.domain.interactors.characters.ICharactersInteractor
import xyz.mmixel.domain.interactors.episodes.EpisodesInteractor
import xyz.mmixel.domain.interactors.episodes.IEpisodesInteractor
import xyz.mmixel.domain.interactors.locations.ILocationsInteractor
import xyz.mmixel.domain.interactors.locations.LocationsInteractor
import xyz.mmixel.domain.repository.IRickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerModule::class])
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Module
    interface InnerModule {
        @Binds
        @Singleton
        fun provideRepository(repository: RickAndMortyRepository): IRickAndMortyRepository

        @Binds
        fun provideCharactersInteractor(charactersInteractor: CharactersInteractor): ICharactersInteractor

        @Binds
        fun provideEpisodesInteractor(episodesInteractor: EpisodesInteractor): IEpisodesInteractor

        @Binds
        fun provideLocationsInteractor(locationsInteractor: LocationsInteractor): ILocationsInteractor

        @Binds
        fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
    }
}