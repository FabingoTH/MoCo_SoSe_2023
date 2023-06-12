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
    fun insertHighscore(highscore : Highscore)

    @Delete
    fun deleteHighscores(highscores : List<Highscore>)

    @Query("SELECT date, score FROM highscore ORDER BY score ASC")
    fun getHighscoresByHighest() : List<Highscore>
}