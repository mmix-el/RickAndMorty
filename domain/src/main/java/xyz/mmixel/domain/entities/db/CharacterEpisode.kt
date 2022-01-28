package xyz.mmixel.domain.entities.db

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisode(
    val characterId: Int,
    val episodeId: Int
)