package xyz.mmixel.data.cache

import androidx.room.*
import xyz.mmixel.domain.entities.db.*

@Dao
interface ApplicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterEpisode(characterEpisode: CharacterEpisode)

    @Query("SELECT * FROM episodes WHERE episodeId = :id")
    suspend fun getEpisodeById(id: Int): Episode

    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationById(id: Int): Location

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<Character>

    @Query("SELECT * FROM episodes")
    suspend fun getEpisodes(): List<Episode>

    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<Location>

    @Query("SELECT * FROM characters WHERE name LIKE IFNULL(:name, '%%') AND status LIKE IFNULL(:status, '%%') AND species LIKE IFNULL(:species, '%%') AND type LIKE IFNULL(:type, '%%') AND gender LIKE IFNULL(:gender, '%%')")
    suspend fun queryCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): List<Character>

    @Query("SELECT * FROM episodes WHERE name LIKE IFNULL(:name, '%%') AND episode LIKE IFNULL(:episode, '%%')")
    suspend fun queryEpisodes(
        name: String?,
        episode: String?
    ): List<Episode>

    @Query("SELECT * FROM locations WHERE name LIKE IFNULL(:name, '%%') AND type LIKE IFNULL(:type, '%%') AND dimension LIKE IFNULL(:dimension, '%%')")
    suspend fun queryLocations(
        name: String?,
        type: String?,
        dimension: String?
    ): List<Location>

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :id")
    suspend fun getCharacterWithEpisodes(id: Int): CharacterWithEpisodes

    @Transaction
    @Query("SELECT * FROM episodes WHERE episodeId = :id")
    suspend fun getEpisodeWithCharacters(id: Int): EpisodeWithCharacters

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :id")
    suspend fun getCharacterWithLocation(id: Int): CharacterWithOriginWithLocation

    @Transaction
    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationWithResidents(id: Int): LocationWithResidents
}