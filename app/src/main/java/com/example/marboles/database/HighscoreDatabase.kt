package com.example.marboles.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HighscoreEntry::class],
    exportSchema = false,
    version = 10
)
abstract class HighscoreDatabase : RoomDatabase(){
    abstract fun highscoreDao() : HighscoreDao
}