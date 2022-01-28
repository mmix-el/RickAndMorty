package xyz.mmixel.domain.entities.network.locations

import com.google.gson.annotations.SerializedName
import xyz.mmixel.domain.entities.db.Location

data class LocationDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("residents")
    val residents: List<String>
) {
    fun toLocationEntity() =
        Location(
            id,
            name,
            type,
            dimension
        )
}
