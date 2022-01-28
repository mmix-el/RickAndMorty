package xyz.mmixel.rickandmorty.ui.di.locations

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsViewModel(locationsViewModel: LocationsViewModel): ViewModel
}