package xyz.mmixel.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.domain.entities.network.locations.LocationDetails

interface IRickAndMortyRepository {

    fun getCharacters(): LiveData<PagingData<CharacterDetails>>

    suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    fun queryCharacters(
        characterQuery: CharacterQuery
    ): LiveData<PagingData<CharacterDetails>>

    suspend fun getCharacter(id: Int): CharacterDetails?

    fun getEpisodes(): LiveData<PagingData<EpisodeDetails>>

    suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String): List<EpisodeDetails>

    suspend fun getEpisode(id: Int): EpisodeDetails?

    fun queryEpisodes(
        episodeQuery: EpisodeQuery
    ): LiveData<PagingData<EpisodeDetails>>

    fun getLocations(): LiveData<PagingData<LocationDetails>>

    fun queryLocations(
        locationQuery: LocationQuery
    ): LiveData<PagingData<LocationDetails>>

    suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    suspend fun getLocation(id: Int): LocationDetails?
}