package xyz.mmixel.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.domain.interactors.characters.ICharactersInteractor
import xyz.mmixel.rickandmorty.di.Injector
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    val charactersInteractor: ICharactersInteractor
) : ViewModel() {

    fun getCharacters() = charactersInteractor.getCharacters().cachedIn(viewModelScope)

    fun getFilteredCharacters(charactersQuery: CharacterQuery) =
        charactersInteractor.getFilteredCharacters(charactersQuery).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharactersFragmentComponent()
    }
}