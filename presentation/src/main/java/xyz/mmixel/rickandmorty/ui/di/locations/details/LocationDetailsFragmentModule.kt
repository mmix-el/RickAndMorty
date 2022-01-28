package xyz.mmixel.rickandmorty.ui.di.locations.details

import androidx.lifecycle.ViewModel
import xyz.mmixel.rickandmorty.di.ViewModelKey
import xyz.mmixel.rickandmorty.ui.viewmodel.details.LocationDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationDetailsFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    fun bindLocationDetailsViewModel(locationDetailsViewModel: LocationDetailsViewModel): ViewModel
}