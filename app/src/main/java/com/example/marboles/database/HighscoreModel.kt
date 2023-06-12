package com.example.marboles.database

import androidx.room.*

@Entity(tableName = "highscore")
data class Highscore (
    @PrimaryKey(autoGenerate = false) val id : Long? = null,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "score") val score: Int // Time in Sekunden
)