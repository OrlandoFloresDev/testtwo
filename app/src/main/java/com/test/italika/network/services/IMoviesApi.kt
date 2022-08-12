package com.test.italika.network.services

import com.test.italika.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IMoviesApi {
    @GET("movie/{category}")
    suspend fun getAPIMovies(@Path(value = "category") category: String,
                             @Query("api_key") api_key: String,
                             @Query("page") page: Int): Movies
}