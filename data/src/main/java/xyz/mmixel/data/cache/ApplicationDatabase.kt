package xyz.mmixel.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.mmixel.domain.entities.db.Character
import xyz.mmixel.domain.entities.db.CharacterEpisode
import xyz.mmixel.domain.entities.db.Episode
import xyz.mmixel.domain.entities.db.Location

@Database(
    entities = [
        Character::class,
        Episode::class,
        Location::class,
        CharacterEpisode::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val applicationDao: ApplicationDao
}