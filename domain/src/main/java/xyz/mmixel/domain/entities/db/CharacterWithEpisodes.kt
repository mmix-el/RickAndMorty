package xyz.mmixel.domain.entities.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CharacterWithEpisodes(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(CharacterEpisode::class)
    )
    val episodes: List<Episode>
)
