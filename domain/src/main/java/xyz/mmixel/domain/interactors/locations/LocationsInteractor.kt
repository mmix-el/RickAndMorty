package xyz.mmixel.domain.interactors.locations

import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.domain.repository.IRickAndMortyRepository
import javax.inject.Inject

class LocationsInteractor @Inject constructor(
    private val repository: IRickAndMortyRepository
) : ILocationsInteractor {

    override fun getLocations() = repository.getLocations()

    override fun getFilteredLocations(locationQuery: LocationQuery) =
        repository.queryLocations(locationQuery)

    override suspend fun getLocationById(id: Int) = repository.getLocation(id)
}