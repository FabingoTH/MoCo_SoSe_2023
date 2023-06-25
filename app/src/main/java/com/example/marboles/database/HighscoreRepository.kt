package com.example.marboles.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HighscoreRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        HighscoreDatabase::class.java, "highscore"
    ).build()

    private val highscoreDao = db.highscoreDao()


    suspend fun getAllHighscores(): List<Highscore>? {
        return withContext(Dispatchers.IO) {
            highscoreDao.getHighscoresByHighest()
        }
    }

    suspend fun addHighscore(highscore: Highscore) {
            highscoreDao.insertHighscore(highscore)
    }

    suspend fun deleteAllHighscores(highscores: List<Highscore>) {
        withContext(Dispatchers.IO) {
            highscoreDao.deleteHighscores(highscores)
        }
    }

    suspend fun addSampleHighscore() {
        val highscore = Highscore(date = "10.06.2023", score = 30)
        withContext(Dispatchers.IO) {
            highscoreDao.insertHighscore(highscore)
        }
    }

}