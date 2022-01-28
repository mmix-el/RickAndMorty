package xyz.mmixel.rickandmorty.ui.di.characters.details

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.characters.CharacterDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharacterDetailsFragmentModule::class])
interface CharacterDetailsFragmentComponent {
    fun inject(characterDetailsFragment: CharacterDetailsFragment)
}