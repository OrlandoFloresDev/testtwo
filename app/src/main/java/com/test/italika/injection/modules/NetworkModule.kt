package com.test.italika.injection.modules

import com.test.italika.network.services.IMoviesApi
import com.test.italika.network.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun getFeedsService(@Named(RetrofitModule.PUBLIC_CLIENT) retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun getMoviesService(@Named(RetrofitModule.PUBLIC_CLIENT) retrofit: Retrofit): IMoviesApi {
        return retrofit.create(IMoviesApi::class.java)
    }
}