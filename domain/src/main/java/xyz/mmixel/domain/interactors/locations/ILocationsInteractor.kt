package xyz.mmixel.domain.interactors.locations

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.entities.network.locations.LocationDetails

interface ILocationsInteractor {
    fun getLocations(): LiveData<PagingData<LocationDetails>>
    fun getFilteredLocations(locationQuery: LocationQuery): LiveData<PagingData<LocationDetails>>
    suspend fun getLocationById(id: Int): LocationDetails?
}