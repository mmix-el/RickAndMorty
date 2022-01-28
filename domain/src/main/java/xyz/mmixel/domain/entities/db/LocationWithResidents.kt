package xyz.mmixel.domain.entities.db

import androidx.room.Embedded
import androidx.room.Relation

data class LocationWithResidents(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val residents: List<Character>
)