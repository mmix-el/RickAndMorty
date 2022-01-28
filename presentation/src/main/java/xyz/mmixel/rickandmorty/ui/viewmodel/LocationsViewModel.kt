package xyz.mmixel.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.interactors.locations.LocationsInteractor
import xyz.mmixel.rickandmorty.di.Injector
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    val locationsInteractor: LocationsInteractor
) : ViewModel() {

    fun getLocations() = locationsInteractor.getLocations().cachedIn(viewModelScope)

    fun getFilteredLocations(locationQuery: LocationQuery) =
        locationsInteractor.getFilteredLocations(locationQuery).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationsFragmentComponent()
    }
}