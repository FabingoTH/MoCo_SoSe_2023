package com.example.marboles.mvvm

class Hole (
    val radius: Float,
    val centerX : Float,
    val centerY : Float,
)

val hole0 = Hole(30f, -250f, -20f)
val hole1 = Hole(30f, -220f, 120f)
val hole2 = Hole(30f, 190f, -50f)

val hole3 = Hole(30f, 260f, -90f)
val hole4 = Hole(30f, 260f, 90f)

val hole5 = Hole(30f, 320f, -120f)
val hole6 = Hole(30f, 200f, 0f)
val hole7 = Hole(30f, -330f, -130f)


val holesLevelOne = listOf(hole0, hole1, hole2)
val holesLevelTwo = listOf(hole3, hole4)
val holesLevelThree = listOf(hole5, hole6, hole7)
val holesLevelFour = listOf(hole5, hole6, hole7) // // Platzhalter. TODO: Mit echtem Leveldesign ersetzen
val holesLevelFive = listOf(hole5, hole6, hole7) // TODO