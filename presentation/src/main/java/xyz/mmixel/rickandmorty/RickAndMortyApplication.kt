package xyz.mmixel.rickandmorty

import android.app.Application
import xyz.mmixel.rickandmorty.di.Injector

class RickAndMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Injector.init(this)
    }
}