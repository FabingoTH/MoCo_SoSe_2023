package com.example.marboles.gamemanager

class Wall(
    val wallLeftX: Float,
    val wallRightX: Float,
    val wallTopY: Float,
    val wallBottomY: Float
) {
}

val wall0 = Wall(40f, 90f, 160f, 60f) // OG Wand
val wall1 = Wall(40f, 290f, 60f, 10f)
val wall2 = Wall(-200f, 90f, 10f, -40f)
val wall3 = Wall(-160f, -110f, 120f, 70f)

var walls = listOf(wall0, wall1, wall2, wall3)