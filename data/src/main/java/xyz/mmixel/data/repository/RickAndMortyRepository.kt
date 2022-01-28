package xyz.mmixel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import xyz.mmixel.data.cache.Cache
import xyz.mmixel.data.network.Network
import xyz.mmixel.data.paging.characters.CharactersPagingSource
import xyz.mmixel.data.paging.characters.QueriedCharactersPagingSource
import xyz.mmixel.data.paging.episodes.EpisodesPagingSource
import xyz.mmixel.data.paging.episodes.QueriedEpisodesPagingSource
import xyz.mmixel.data.paging.locations.QueriedLocationsPagingSource
import xyz.mmixel.data.paging.locations.LocationsPagingSource
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.domain.entities.network.characters.Place
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.domain.entities.network.locations.LocationDetails
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.repository.IRickAndMortyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepository @Inject constructor(
    private val cache: Cache,
    private val network: Network
) : IRickAndMortyRepository {

    override suspend fun getCharacter(id: Int): CharacterDetails? {
        var characterDetails: CharacterDetails? = null

        try {
            val response = network.getCharacter(id)

            response.body()?.let {
                characterDetails = it
            }
        } catch (e: Exception) {
            val characterEntity = cache.getCharacterWithLocation(id)

            val character = characterEntity.character
            val origin = characterEntity.origin
            val location = characterEntity.location

            characterDetails = CharacterDetails(
                character.characterId,
                character.name,
                character.status,
                character.species,
                character.type,
                character.gender,
                if (origin == null) Place("unknown", "") else Place(
                    origin.name,
                    origin.id.toString()
                ),
                if (location == null) Place("unknown", "") else Place(
                    location.name,
                    location.id.toString()
                ),
                character.image,
                emptyList()
            )
        }

        return characterDetails
    }

    override fun getCharacters() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { CharactersPagingSource(cache, network) }
    ).liveData

    override fun queryCharacters(characterQuery: CharacterQuery) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { QueriedCharactersPagingSource(characterQuery, cache, network) }
    ).liveData

    override suspend fun getEpisode(id: Int): EpisodeDetails? {
        var episodeDetails: EpisodeDetails? = null

        try {
            val response = network.getEpisode(id)

            response.body()?.let {
                episodeDetails = it
            }
        } catch (e: Exception) {
            val episodeEntity = cache.getEpisodeById(id)

            episodeDetails = EpisodeDetails(
                episodeEntity.episodeId,
                episodeEntity.name,
                episodeEntity.airDate,
                episodeEntity.episode,
                emptyList()
            )
        }

        return episodeDetails
    }

    override fun getEpisodes() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { EpisodesPagingSource(cache, network) }
    ).liveData


    override fun queryEpisodes(episodeQuery: EpisodeQuery) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { QueriedEpisodesPagingSource(episodeQuery, cache, network) }
    ).liveData

    override suspend fun getLocation(id: Int): LocationDetails? {
        var locationDetails: LocationDetails? = null

        try {
            val response = network.getLocation(id)

            response.body()?.let {
                locationDetails = it
            }
        } catch (e: Exception) {
            val locationEntity = cache.getLocationById(id)

            locationDetails = LocationDetails(
                locationEntity.id,
                locationEntity.name,
                locationEntity.type,
                locationEntity.dimension,
                emptyList()
            )
        }

        return locationDetails
    }

    override fun getLocations() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { LocationsPagingSource(cache, network) }
    ).liveData

    override fun queryLocations(locationQuery: LocationQuery) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 20),
        pagingSourceFactory = { QueriedLocationsPagingSource(locationQuery, cache, network) }
    ).liveData

    override suspend fun getMultipleEpisodes(
        characterId: Int,
        episodesIds: String
    ): List<EpisodeDetails> {
        var episodes = emptyList<EpisodeDetails>()

        try {
            val response = network.getMultipleEpisodes(episodesIds)

            response.body()?.let {
                episodes = it
            }
        } catch (e: Exception) {
            val characterWithEpisodes = cache.getCharacterWithEpisodes(characterId)
            episodes = characterWithEpisodes.episodes.map {
                EpisodeDetails(
                    it.episodeId,
                    it.name,
                    it.airDate,
                    it.episode,
                    emptyList()
                )
            }
        }
        return episodes
    }

    override suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails> {
        var characters = emptyList<CharacterDetails>()

        try {
            val response = network.getMultipleCharacters(charactersIds)

            response.body()?.let {
                characters = it
            }
        } catch (e: Exception) {
            val episodeWithCharacters = cache.getEpisodeWithCharacters(episodeId)
            characters = episodeWithCharacters.characters.map {
                CharacterDetails(
                    it.characterId,
                    it.name,
                    it.status,
                    it.species,
                    it.type,
                    it.gender,
                    Place("", ""),
                    Place("", ""),
                    it.image,
                    emptyList()
                )
            }
        }
        return characters
    }

    override suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        charactersIds: String
    ): List<CharacterDetails> {
        var characters = emptyList<CharacterDetails>()

        try {
            val response = network.getMultipleCharacters(charactersIds)

            response.body()?.let {
                characters = it
            }
        } catch (e: Exception) {
            val locationWithResidents = cache.getLocationWithResidents(locationId)
            characters = locationWithResidents.residents.map {
                CharacterDetails(
                    it.characterId,
                    it.name,
                    it.status,
                    it.species,
                    it.type,
                    it.gender,
                    Place("", ""),
                    Place("", ""),
                    it.image,
                    emptyList()
                )
            }
        }
        return characters
    }
}