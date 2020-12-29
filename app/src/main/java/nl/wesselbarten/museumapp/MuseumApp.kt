package nl.wesselbarten.museumapp

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MuseumApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}