package xyz.mmixel.data.entities

import com.google.gson.annotations.SerializedName
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails

data class Episodes(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<EpisodeDetails>
)