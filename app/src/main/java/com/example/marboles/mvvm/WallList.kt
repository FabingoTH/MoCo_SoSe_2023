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
//var wallsLevelTwo = listOf(wall0, wall1) // Nur zum Testen, Platzhalter

// ich probiere hier nur mal ne andere Schreibweise aus, Collisions müssten dann noch funktionieren oder?

var wallsLevelTwo = listOf(
    Wall(-380f, 300f, 5f, -5f), // OG Wand
    Wall(-200f, -190f, 110f, -110f),
    Wall(100f, 110f, 110f, -110f),
    Wall(-5f, 5f, 380f, 300f),
    Wall(-5f, 5f, -300f, -380f)
)

var wallsLevelThree = listOf(
    Wall(-380f, -50f, -70f, -80f),
    Wall(-270f, -260f, 70f, -70f),
    Wall(-150f, -140f, 180f, 40f),
    Wall(20f, 30f, 180f, -70f),
    Wall(120f, 130f, 180f, 60f),
    Wall(120f, 130f, -60f, -180f),
    Wall(200f, 380f, -40f, -50f)
)

var wallsLevelFour = listOf(
    Wall(-380f, -50f, -70f, -80f),
    Wall(-270f, -260f, 70f, -70f),
    Wall(-150f, -140f, 180f, 40f),
    Wall(20f, 30f, 180f, -70f),
    Wall(120f, 130f, 180f, 60f),
    Wall(120f, 130f, -60f, -180f),
    Wall(200f, 380f, -40f, -50f)
)

var wallsLevelFive = listOf(
    Wall(-380f, -50f, -70f, -80f),
    Wall(-270f, -260f, 70f, -70f),
    Wall(-150f, -140f, 180f, 40f),
    Wall(20f, 30f, 180f, -70f),
    Wall(120f, 130f, 180f, 60f),
    Wall(120f, 130f, -60f, -180f),
    Wall(200f, 380f, -40f, -50f)
)

var borderwalls = listOf(borderleft, borderright, bordertop, borderbottom)
