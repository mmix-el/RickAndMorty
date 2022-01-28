package xyz.mmixel.rickandmorty.di.modules

import android.content.Context
import androidx.room.Room
import xyz.mmixel.data.cache.Cache
import xyz.mmixel.data.cache.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun cache(context: Context): Cache {
        val database =
            Room.databaseBuilder(context, ApplicationDatabase::class.java, DATABASE_NAME).build()
        return Cache(database.applicationDao)
    }

    private companion object {
        const val DATABASE_NAME = "rick_and_morty_database"
    }
}