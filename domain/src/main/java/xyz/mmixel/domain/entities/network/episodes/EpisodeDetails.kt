package xyz.mmixel.domain.entities.network.episodes

import com.google.gson.annotations.SerializedName
import xyz.mmixel.domain.entities.db.Episode

data class EpisodeDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val characters: List<String>
) {
    fun toEpisodeEntity() =
        Episode(
            id,
            name,
            airDate,
            episode
        )
}
