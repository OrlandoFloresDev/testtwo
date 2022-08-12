package com.test.italika.models.room

import androidx.room.*
import com.test.italika.models.Movie

@Dao
@TypeConverters(Converters::class)
interface MovieDao {
    @Query("SELECT * from movies")
    suspend fun getMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>): List<Long>

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun countOfRows(): Int
}