package xyz.mmixel.rickandmorty.ui.di.locations

import xyz.mmixel.rickandmorty.di.FragmentScope
import xyz.mmixel.rickandmorty.ui.locations.LocationsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationsFragmentModule::class])
interface LocationsFragmentComponent {
    fun inject(locationsFragment: LocationsFragment)
}