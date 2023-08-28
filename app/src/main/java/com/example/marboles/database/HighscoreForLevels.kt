package com.example.marboles.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "highscores")
data class HighscoreEntry  (
    @PrimaryKey(autoGenerate = false) val id : Long? = null,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "score") val score: Int, // Time" in Sekunden
    @ColumnInfo(name = "level_id") val levelId: Int // Level-ID
)

