package com.test.italika.datasource

import com.test.italika.BuildConfig
import com.test.italika.models.Movie
import com.test.italika.network.services.IMoviesApi
import kotlinx.coroutines.*
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor(
    private val api: IMoviesApi
) : RemoteSource {

    override suspend fun retrieveData(category: String): List<Movie> = withContext(Dispatchers.IO) {
        val list = api.getAPIMovies(category, BuildConfig.API_KEY, 1).results
        list
    }
}