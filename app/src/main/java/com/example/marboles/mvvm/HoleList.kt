package com.example.marboles.mvvm

class Hole (
    val radius: Float,
    val centerX : Float,
    val centerY : Float,
)

val hole0 = Hole(30f, -250f, -20f)
val hole1 = Hole(30f, -220f, 120f)
val hole2 = Hole(30f, 190f, -50f)

val hole3 = Hole(30f, 260f, -90f) // Platzhalter. TODO: Mit echtem Leveldesign ersetzen
val hole4 = Hole(30f, 260f, 90f)

val holesLevelOne = listOf(hole0, hole1, hole2)
val holesLevelTwo = listOf(hole3, hole4) // TODO, siehe oben!
val holesLevelThree = listOf(hole3, hole4) // TODO
val holesLevelFour = listOf(hole3, hole4) // TODO
val holesLevelFive = listOf(hole3, hole4) // TODO