package xyz.mmixel.rickandmorty.ui.di.characters

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.characters.CharactersFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharactersFragmentModule::class])
interface CharactersFragmentComponent {
    fun inject(charactersFragment: CharactersFragment)
}