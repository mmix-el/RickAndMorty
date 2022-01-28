package xyz.mmixel.data.cache

import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.entities.db.Character
import xyz.mmixel.domain.entities.db.CharacterEpisode
import xyz.mmixel.domain.entities.db.Episode
import xyz.mmixel.domain.entities.db.Location

class Cache(private val applicationDao: ApplicationDao) {

    suspend fun insertCharacter(character: Character) =
        applicationDao.insertCharacter(character)

    suspend fun insertEpisode(episode: Episode) =
        applicationDao.insertEpisode(episode)

    suspend fun insertLocation(location: Location) = applicationDao.insertLocation(location)

    suspend fun insertCharacterEpisode(characterEpisode: CharacterEpisode) =
        applicationDao.insertCharacterEpisode(characterEpisode)

    suspend fun getCharacters() = applicationDao.getCharacters()

    suspend fun getEpisodes() = applicationDao.getEpisodes()

    suspend fun getLocations() = applicationDao.getLocations()

    suspend fun queryCharacters(characterQuery: CharacterQuery) =
        applicationDao.queryCharacters(
            characterQuery.name,
            characterQuery.status,
            characterQuery.species,
            characterQuery.type,
            characterQuery.gender
        )

    suspend fun queryEpisodes(episodeQuery: EpisodeQuery) =
        applicationDao.queryEpisodes(
            episodeQuery.name,
            episodeQuery.episode
        )

    suspend fun queryLocations(locationQuery: LocationQuery) =
        applicationDao.queryLocations(
            locationQuery.name,
            locationQuery.type,
            locationQuery.dimension
        )

    suspend fun getEpisodeById(id: Int) =
        applicationDao.getEpisodeById(id)

    suspend fun getLocationById(id: Int) = applicationDao.getLocationById(id)

    suspend fun getCharacterWithEpisodes(id: Int) = applicationDao.getCharacterWithEpisodes(id)

    suspend fun getEpisodeWithCharacters(id: Int) = applicationDao.getEpisodeWithCharacters(id)

    suspend fun getCharacterWithLocation(id: Int) = applicationDao.getCharacterWithLocation(id)

    suspend fun getLocationWithResidents(id: Int) = applicationDao.getLocationWithResidents(id)
}