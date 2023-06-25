package com.example.marboles.gamemanager

class Hole (
    val radius: Float,
    val centerX : Float,
    val centerY : Float,
)

val hole0 = Hole(30f, -250f, -20f)
val hole1 = Hole(30f, -220f, 120f)
val hole2 = Hole(30f, 190f, -50f)

val holes = listOf(hole0, hole1, hole2)