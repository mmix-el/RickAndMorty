package xyz.mmixel.domain.entities.network.characters

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
