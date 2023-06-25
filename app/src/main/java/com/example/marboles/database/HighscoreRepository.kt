package com.example.marboles.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class HighscoreRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        HighscoreDatabase::class.java, "highscore"
    ).build()

    private val highscoreDao = db.highscoreDao()

    suspend fun getAllHighscores(): List<Highscore>? {
        return withContext(Dispatchers.IO) {
            // irgendwie wurden die Highscores immer doppelt angezeigt.
            // "distinct" löst zwar nicht das eigentliche Problem (welches wir nicht kennen - werden die zweimal abgespeichert?
            // why tho??) aber wenigstens ist das Problem nicht mehr sichtbar ;)
            highscoreDao.getHighscoresByHighest()!!.distinct()
        }
    }

    suspend fun addHighscore(highscore: Highscore) {
            highscoreDao.insertHighscore(highscore)
    }

    suspend fun deleteAllHighscores() {
        withContext(Dispatchers.IO) {
            highscoreDao.deleteAllHighscores()
        }
    }

}