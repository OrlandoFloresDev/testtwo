package com.test.italika.datasource

import com.test.italika.models.Movie

interface Source {
    suspend fun retrieveData(category: String): List<Movie>
}

interface LocalSource : Source {
    suspend fun refreshData(movies: List<Movie>)
}

interface RemoteSource : Source