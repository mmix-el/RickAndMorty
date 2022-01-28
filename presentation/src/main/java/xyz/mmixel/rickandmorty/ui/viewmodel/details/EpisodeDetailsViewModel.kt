package xyz.mmixel.rickandmorty.ui.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.domain.interactors.characters.ICharactersInteractor
import xyz.mmixel.domain.interactors.episodes.EpisodesInteractor
import xyz.mmixel.domain.utils.RegexUtil
import xyz.mmixel.rickandmorty.di.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    val episodesInteractor: EpisodesInteractor,
    val charactersInteractor: ICharactersInteractor
) : ViewModel() {

    private val _episodeDetailsLiveData = MutableLiveData<EpisodeDetails>()
    val episodeDetailsLiveData: LiveData<EpisodeDetails>
        get() = _episodeDetailsLiveData
    private val _charactersLiveData = MutableLiveData<List<CharacterDetails>>()
    val charactersLiveData: LiveData<List<CharacterDetails>>
        get() = _charactersLiveData

    fun getEpisodeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val episodeDetails = episodesInteractor.getEpisodeById(id)

            episodeDetails?.let {
                _episodeDetailsLiveData.postValue(it)

                val charactersIds = ArrayList<Int>()
                episodeDetails.characters.forEach { url ->
                    charactersIds.add(RegexUtil.extractIdFromUrl(url))
                }

                if (charactersIds.size == 1) {
                    getCharacterById(charactersIds[0])
                } else {
                    getMultipleCharacters(
                        episodeDetails.id,
                        RegexUtil.extractFromBrackets(charactersIds.toString())
                    )
                }
            }
        }
    }

    private suspend fun getMultipleCharacters(episodeId: Int, charactersIds: String) {
        val characters =
            charactersInteractor.getMultipleCharactersInEpisode(episodeId, charactersIds)

        _charactersLiveData.postValue(characters)
    }

    private suspend fun getCharacterById(id: Int) {
        val character = charactersInteractor.getCharacterById(id)

        character?.let {
            _charactersLiveData.postValue(listOf(it))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodeDetailsFragmentComponent()
    }
}