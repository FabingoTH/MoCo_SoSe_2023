package com.example.marboles.database

import androidx.room.*

@Entity(tableName = "highscore")
data class Highscore (
    @PrimaryKey(autoGenerate = false) val id : Long? = null,
    val date: String,
    val score: Int // Time in Sekunden
)