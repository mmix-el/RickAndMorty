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

class CharacterDetailsViewModel @Inject constructor(
    val charactersInteractor: ICharactersInteractor,
    val episodesInteractor: EpisodesInteractor
) : ViewModel() {

    private val _characterDetailsLiveData = MutableLiveData<CharacterDetails>()
    val characterDetailsLiveData: LiveData<CharacterDetails>
        get() = _characterDetailsLiveData
    private val _episodesLiveData = MutableLiveData<List<EpisodeDetails>>()
    val episodesLiveData: LiveData<List<EpisodeDetails>>
        get() = _episodesLiveData

    fun getCharacterById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val characterDetails = charactersInteractor.getCharacterById(id)

            characterDetails?.let {
                _characterDetailsLiveData.postValue(it)

                val episodesIds = ArrayList<Int>()
                characterDetails.episode.forEach { url ->
                    episodesIds.add(RegexUtil.extractIdFromUrl(url))
                }

                if (episodesIds.size == 1) {
                    getEpisodeById(episodesIds[0])
                } else {
                    getMultipleEpisodes(
                        characterDetails.id,
                        RegexUtil.extractFromBrackets(episodesIds.toString())
                    )
                }
            }
        }
    }

    private suspend fun getMultipleEpisodes(characterId: Int, episodesIds: String) {
        val episodes = episodesInteractor.getMultipleEpisodes(characterId, episodesIds)

        _episodesLiveData.postValue(episodes)
    }

    private suspend fun getEpisodeById(id: Int) {
        val episode = episodesInteractor.getEpisodeById(id)

        episode?.let {
            _episodesLiveData.postValue(listOf(it))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharacterDetailsFragmentComponent()
    }
}