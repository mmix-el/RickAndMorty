package xyz.mmixel.domain.entities.db

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithOriginWithLocation(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "originId",
        entityColumn = "id"
    )
    val origin: Location?,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "id"
    )
    val location: Location?
)
