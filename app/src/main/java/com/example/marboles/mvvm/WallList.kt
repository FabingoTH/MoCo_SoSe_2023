package com.example.marboles.mvvm

class Wall(
    val wallLeftX: Float,
    val wallRightX: Float,
    val wallTopY: Float,
    val wallBottomY: Float
)

val wall0 = Wall(80f, 90f, 180f, -100f) // OG Wand
val wall1 = Wall(90f, 290f, 60f, 50f)
val wall2 = Wall(-200f, 80f, 10f, 0f)
val wall3 = Wall(-160f, -150f, 120f, 70f)
val wall4 = Wall(0f, 50f, 0f, 50f) // Quadrat als Referenzpunkt in der Mitte des Screens

//borderwalls
val borderleft = Wall(-380f, -370f,180f,-180f)
val borderright = Wall(370f, 380f, 180f, -180f)
val borderbottom = Wall(-380f, 380f, 190f, 180f)
val bordertop = Wall(-380f, 380f, -190f, -180f)


var wallsLevelOne = listOf(wall0, wall1, wall2)
var wallsLevelTwo = listOf(wall0, wall1) // Nur zum Testen, Platzhalter

var borderwalls = listOf(borderleft, borderright, bordertop, borderbottom)
