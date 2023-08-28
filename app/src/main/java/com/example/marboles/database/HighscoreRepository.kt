package com.example.marboles.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class HighscoreRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        HighscoreDatabase::class.java, "highscores"
    ).build()

    private val highscoreDao = db.highscoreDao()

    suspend fun deleteAllHighscores() {
        withContext(Dispatchers.IO) {
            highscoreDao.deleteAllHighscores()
        }
    }

    suspend fun getHighscoresByLevel(levelId: Int): List<HighscoreEntry>? {
        return withContext(Dispatchers.IO) {
            val rawHighscores = highscoreDao.getHighscoresByLevelAndHighest(levelId)
            rawHighscores?.distinctBy { it.date to it.score }
        }
    }

    suspend fun addHighscore(highscore: HighscoreEntry) {
        highscoreDao.insertHighscore(highscore)
        println("Highscore added:" + highscore)
    }

}