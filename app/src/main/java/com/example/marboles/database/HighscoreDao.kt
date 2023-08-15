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

    @Query("DELETE FROM highscore")
    suspend fun deleteAllHighscores()

    @Query("SELECT date, score FROM highscore ORDER BY score ASC")
    suspend fun getHighscoresByHighest() : List<Highscore>?
}

