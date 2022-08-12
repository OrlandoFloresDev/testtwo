package com.test.italika.injection.modules

import android.content.Context
import androidx.room.Room
import com.test.italika.datasource.MovieLocalSource
import com.test.italika.models.room.MovieDao
import com.test.italika.models.room.MovieRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    companion object {
        const val databaseName = "product_database"
    }

    @Provides
    @Singleton
    fun provideDataBase( @ApplicationContext context: Context): MovieRoomDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieRoomDataBase::class.java,
            databaseName
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(movieRoomDataBase: MovieRoomDataBase): MovieDao =
        movieRoomDataBase.movieDao()

    @Provides
    @Singleton
    fun provideLocalSource(movieDao: MovieDao): MovieLocalSource =
        MovieLocalSource(movieDao)
}