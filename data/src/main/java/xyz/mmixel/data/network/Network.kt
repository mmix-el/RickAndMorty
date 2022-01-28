package xyz.mmixel.data.network

import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.queries.LocationQuery

class Network(private val api: RickAndMortyApi) {

    suspend fun getCharacters(
        page: Int?,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) = api.getCharacters(page, name, status, species, type, gender)

    suspend fun getEpisodes(
        page: Int? = null,
        name: String? = null,
        episode: String? = null,
    ) = api.getEpisodes(page, name, episode)

    suspend fun getLocations(
        page: Int? = null,
        name: String? = null,
        type: String? = null,
        dimension: String? = null,
    ) = api.getLocations(page, name, type, dimension)

    suspend fun getCharacter(id: Int) = api.getCharacter(id)

    suspend fun getEpisode(id: Int) = api.getEpisode(id)

    suspend fun getLocation(id: Int) = api.getLocation(id)

    suspend fun queryCharacters(
        query: CharacterQuery,
        page: Int
    ) = api.getCharacters(
        page,
        query.name,
        query.status,
        query.species,
        query.type,
        query.gender
    )

    suspend fun queryEpisodes(
        query: EpisodeQuery,
        page: Int
    ) = api.getEpisodes(
        page,
        query.name,
        query.episode
    )

    suspend fun queryLocations(
        query: LocationQuery,
        page: Int
    ) = api.getLocations(
        page,
        query.name,
        query.type,
        query.dimension
    )

    suspend fun getMultipleCharacters(ids: String) =
        api.getMultipleCharacters(ids)

    suspend fun getMultipleEpisodes(ids: String) =
        api.getMultipleEpisodes(ids)
}