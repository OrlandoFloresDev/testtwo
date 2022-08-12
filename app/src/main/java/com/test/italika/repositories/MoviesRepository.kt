package com.test.italika.repositories

import com.test.italika.datasource.MovieLocalSource
import com.test.italika.datasource.MoviesRemoteSource
import com.test.italika.models.Movie
import com.test.italika.managers.NetManager
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val netManager: NetManager,
    private val localSource: MovieLocalSource,
    private val remoteSource: MoviesRemoteSource
) {

     suspend fun getMovieList(category: String): List<Movie> {

        return if (netManager.isConnectedToInternet)
            retrieveRemoteData(category)
        else
            retrieveLocalData(category)

    }

    private suspend fun retrieveRemoteData(category: String): List<Movie> {

        val movies = remoteSource.retrieveData(category)
        localSource.refreshData(movies)
        return movies
    }

    private suspend fun retrieveLocalData(category: String): List<Movie> =
        localSource.retrieveData(category)
}
