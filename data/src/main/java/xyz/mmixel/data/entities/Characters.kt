package xyz.mmixel.data.entities

import com.google.gson.annotations.SerializedName
import xyz.mmixel.domain.entities.network.characters.CharacterDetails

data class Characters(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterDetails>
)