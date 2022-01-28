package xyz.mmixel.rickandmorty.ui.di.characters.details

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.details.CharacterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharacterDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    fun bindCharacterDetailsViewModel(characterDetailsViewModel: CharacterDetailsViewModel): ViewModel
}