package com.test.italika.injection.modules

import com.test.italika.datasource.MovieLocalSource
import com.test.italika.datasource.MoviesRemoteSource
import com.test.italika.managers.NetManager
import com.test.italika.network.services.UserService
import com.test.italika.repositories.MoviesRepository
import com.test.italika.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule{

    @Provides
    fun getFeedRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }

    @Provides
    fun provideModelRepository(
        netManager: NetManager,
        localSource: MovieLocalSource,
        remoteSource: MoviesRemoteSource
    ): MoviesRepository = MoviesRepository(
        netManager, localSource, remoteSource
    )
}