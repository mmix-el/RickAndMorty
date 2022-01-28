package xyz.mmixel.domain.interactors.characters

import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : ICharactersInteractor {

    override fun getCharacters() = repository.getCharacters()

    override fun getFilteredCharacters(characterQuery: CharacterQuery) =
        repository.queryCharacters(characterQuery)

    override suspend fun getMultipleCharactersInEpisode(episodeId: Int, charactersIds: String) =
        repository.getMultipleCharactersInEpisode(episodeId, charactersIds)

    override suspend fun getMultipleCharactersInLocation(locationId: Int, residentsIds: String) =
        repository.getMultipleCharactersInLocation(locationId, residentsIds)

    override suspend fun getCharacterById(id: Int) = repository.getCharacter(id)
}