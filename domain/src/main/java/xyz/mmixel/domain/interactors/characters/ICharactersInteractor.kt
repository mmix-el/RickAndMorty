package xyz.mmixel.domain.interactors.characters

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.entities.network.characters.CharacterDetails

interface ICharactersInteractor {

    fun getCharacters(): LiveData<PagingData<CharacterDetails>>

    fun getFilteredCharacters(characterQuery: CharacterQuery): LiveData<PagingData<CharacterDetails>>

    suspend fun getMultipleCharactersInEpisode(
        episodeId: Int,
        charactersIds: String
    ): List<CharacterDetails>

    suspend fun getMultipleCharactersInLocation(
        locationId: Int,
        residentsIds: String
    ): List<CharacterDetails>

    suspend fun getCharacterById(id: Int): CharacterDetails?
}