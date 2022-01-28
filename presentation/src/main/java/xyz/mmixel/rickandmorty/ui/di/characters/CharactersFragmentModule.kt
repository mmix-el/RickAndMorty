package xyz.mmixel.rickandmorty.ui.di.characters

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    fun bindCharactersViewModel(charactersViewModel: CharactersViewModel): ViewModel
}