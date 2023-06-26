package com.example.marboles.mvvm

import androidx.compose.ui.unit.dp
class Wall(
    val wallLeftX: Float,
    val wallRightX: Float,
    val wallTopY: Float,
    val wallBottomY: Float
)

val wall0 = Wall(40f, 90f, 250f, -100f) // OG Wand
val wall1 = Wall(42f, 290f, 60f, 10f)
val wall2 = Wall(-200f, 88f, 10f, -40f)
val wall3 = Wall(-160f, -110f, 120f, 70f)
val wall4 = Wall(0f, 50f, 0f, 50f) // Quadrat als Referenzpunkt in der Mitte des Screens

var walls = listOf(wall0, wall1, wall2, wall3)