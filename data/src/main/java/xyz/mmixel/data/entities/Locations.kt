package xyz.mmixel.data.entities

import com.google.gson.annotations.SerializedName
import xyz.mmixel.domain.entities.network.locations.LocationDetails

data class Locations(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<LocationDetails>
)