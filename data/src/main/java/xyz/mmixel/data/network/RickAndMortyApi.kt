package xyz.mmixel.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.mmixel.data.entities.Characters
import xyz.mmixel.data.entities.Episodes
import xyz.mmixel.data.entities.Locations
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.domain.entities.network.locations.LocationDetails

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Characters

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null,
    ): Episodes

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null,
    ): Locations

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterDetails>

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Response<EpisodeDetails>

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Response<LocationDetails>

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") charactersIds: String):
            Response<List<CharacterDetails>>

    @GET("episode/{ids}")
    suspend fun getMultipleEpisodes(@Path("ids") episodesIds: String):
            Response<List<EpisodeDetails>>
}