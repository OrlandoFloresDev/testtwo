package com.test.italika.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.italika.models.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieRoomDataBase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

}