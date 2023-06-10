package com.example.marboles.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Highscore::class],
    exportSchema = false,
    version = 1
)
abstract class HighscoreDatabase : RoomDatabase(){
    abstract fun highscoreDao() : HighscoreDao
}