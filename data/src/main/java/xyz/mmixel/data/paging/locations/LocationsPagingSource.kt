package xyz.mmixel.data.paging.locations

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.mmixel.data.cache.Cache
import xyz.mmixel.data.network.Network
import xyz.mmixel.domain.entities.network.locations.LocationDetails

class LocationsPagingSource(
    private val cache: Cache,
    private val network: Network
) : PagingSource<Int, LocationDetails>() {

    override fun getRefreshKey(state: PagingState<Int, LocationDetails>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDetails> {
        var results: List<LocationDetails>

        val page: Int = params.key ?: FIRST_PAGE_INDEX
        val pageSize = params.loadSize
        var nextKey: Int? = null

        try {
            network.getLocations(page).apply {

                if (this.info.next != null) {
                    val uri = Uri.parse(this.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextKey = nextPageQuery?.toInt()
                }

                results = this.results

                results.forEach { locationDetails ->
                    cache.insertLocation(locationDetails.toLocationEntity())
                }
            }
        } catch (e: Exception) {
            cache.getLocations().apply {
                nextKey = if (size < pageSize) null else nextKey?.plus(1)
                results = this.map { locationEntity ->
                    LocationDetails(
                        locationEntity.id,
                        locationEntity.name,
                        locationEntity.type,
                        locationEntity.dimension,
                        emptyList()
                    )
                }
            }
        }

        return LoadResult.Page(data = results, prevKey = null, nextKey = nextKey)
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}