package com.example.marboles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighscoreDao {
    @Insert
    suspend fun insertHighscore(highscore : HighscoreEntry)

    @Query("DELETE FROM highscores")
    suspend fun deleteAllHighscores()

    @Query("SELECT * FROM highscores WHERE level_id = :levelId ORDER BY score ASC")
    suspend fun getHighscoresByLevelAndHighest(levelId: Int): List<HighscoreEntry>?
}

