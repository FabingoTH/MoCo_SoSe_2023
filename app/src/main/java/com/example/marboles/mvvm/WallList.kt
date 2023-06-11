package com.example.marboles.mvvm

// Ich hab die Wandliste mal zu einer globalen Variable gemacht damit man im Model und im
// View drauf zugreifen kann (Ich weiß nicht ob das von der Architektur her so stimmt? Aber
// soll erst mal funktionieren. Ich checke damit die Kollisionen und zeichne die Wände im View)

class Wall(
    val wallLeftX: Float,
    val wallRightX: Float,
    val wallTopY: Float,
    val wallBottomY: Float
) {
}

val wall0 = Wall(40f, 90f, 110f, 60f) // OG Wand
val wall1 = Wall(15f, 20f, 40f, 60f)
val wall2 = Wall(50f, 60f, 70f, 120f)
val wall3 = Wall(40f, 60f, 30f, 50f)

var walls = listOf(wall0, wall1, wall2, wall3)

