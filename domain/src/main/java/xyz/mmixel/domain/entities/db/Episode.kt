package xyz.mmixel.domain.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey
    val episodeId: Int,
    val name: String,
    val airDate: String,
    val episode: String
)
