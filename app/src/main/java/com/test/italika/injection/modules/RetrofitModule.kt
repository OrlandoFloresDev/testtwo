package com.test.italika.injection.modules

import com.test.italika.BuildConfig
import com.test.italika.datasource.MoviesRemoteSource
import com.test.italika.network.services.IMoviesApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    companion object {
        const val PUBLIC_CLIENT = "publicClient"
        const val AUTH_CLIENT = "authClient"
    }
    private val gson = Gson()
    @Provides
    @Named(PUBLIC_CLIENT)
    fun provideRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->

            val request: Request = chain.request()
                .newBuilder()
                .build()

            chain.proceed(request)
        }


        if (BuildConfig.DEBUG) {
            client.addInterceptor(logging)
        }
        client.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()

    }


    @Provides
    @Named(AUTH_CLIENT)
    fun getAuthRetrofitClient(): Retrofit {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder()

        client.addInterceptor { chain: Interceptor.Chain ->

            //val token = "Bearer ${BuildConfig.BASE_URL}"


            val request: Request = chain.request()
                .newBuilder()
                // .addHeader("Authorization", token)
                .build()

            chain.proceed(request)
        }


        if (BuildConfig.DEBUG) {
            client.addInterceptor(logging)
        }
        client.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)


        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()

    }

    @Provides
    @Singleton
    fun provideRemoteSource(api: IMoviesApi): MoviesRemoteSource = MoviesRemoteSource(api)
}