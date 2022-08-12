package com.test.italika.datasource

import com.test.italika.models.Movie
import com.test.italika.models.room.MovieDao
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieLocalSource @Inject constructor(
    private val movieDao: MovieDao
) : LocalSource {

    override suspend fun retrieveData(category: String): List<Movie> = withContext(Dispatchers.IO) {
        val movies = movieDao.getMovies()

        if (movies.isNotEmpty()) movies
        else emptyList()
    }

    override suspend fun refreshData(movies: List<Movie>) {
        movieDao.insert(movies).size
    }
}