package xyz.mmixel.rickandmorty.ui.di.locations.details

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.locations.LocationDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationDetailsFragmentModule::class])
interface LocationDetailsFragmentComponent {
    fun inject(locationDetailsFragment: LocationDetailsFragment)
}