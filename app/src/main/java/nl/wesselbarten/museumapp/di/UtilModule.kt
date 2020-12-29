package nl.wesselbarten.museumapp.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.wesselbarten.museumapp.presentation.error.ErrorHandler
import nl.wesselbarten.museumapp.util.coroutines.DefaultDispatcherProvider
import nl.wesselbarten.museumapp.util.coroutines.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider
    }

    @Provides
    @Singleton
    fun provideErrorHandler(@ApplicationContext context: Context): ErrorHandler {
        return ErrorHandler(context)
    }
}