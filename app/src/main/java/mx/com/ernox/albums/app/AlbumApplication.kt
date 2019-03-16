package mx.com.ernox.albums.app

import android.app.Application
import mx.com.ernox.albums.di.appModule
import org.koin.android.ext.android.startKoin

class AlbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }
}