package com.example.marboles.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HighscoreDao {
    // Suspend damit DB Operationen fertig laufen können bevor die App weiterläuft
    @Insert
    suspend fun insertHighscore(highscore : Highscore)

    @Delete
    suspend fun deleteHighscores(highscores : List<Highscore>)

    @Query("SELECT date, score FROM highscore ORDER BY score ASC")
    suspend fun getHighscoresByHighest() : List<Highscore>?
}

