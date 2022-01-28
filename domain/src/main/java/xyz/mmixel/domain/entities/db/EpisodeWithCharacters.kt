package xyz.mmixel.domain.entities.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EpisodeWithCharacters(
    @Embedded val episode: Episode,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(CharacterEpisode::class)
    )
    val characters: List<Character>
)