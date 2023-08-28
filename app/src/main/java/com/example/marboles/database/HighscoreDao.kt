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
    suspend fun insertHighscore(highscore : HighscoreEntry)

    @Query("DELETE FROM highscores")
    suspend fun deleteAllHighscores()

    @Query("SELECT * FROM highscores WHERE level_id = :levelId ORDER BY score ASC")
    suspend fun getHighscoresByLevelAndHighest(levelId: Int): List<HighscoreEntry>?
}

