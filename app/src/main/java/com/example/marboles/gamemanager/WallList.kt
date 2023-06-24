package com.example.marboles.gamemanager

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


// drawRect(
//            color = Color.Black,
//            size = Size(width = 50.dp.toPx(), height = 50.dp.toPx()),
//            topLeft = Offset(x = 40.dp.toPx(), y = 60.dp.toPx())
//            wallLeft ergibt sich aus dem x-Wert von topLeft
//            wallbottom aus dem y-Wert von topLeft
//            wallRight ergibt sich aus dem x Wert von topLeft + width des Rechtecks
//            wallTop aus dem y-Wert von topLeft + height des Rechtecks

val wall0 = Wall(40f, 90f, 160f, 60f) // OG Wand
val wall1 = Wall(40f, 290f, 60f, 10f)
val wall2 = Wall(-200f, 90f, 10f, -40f)
val wall3 = Wall(-160f, -110f, 120f, 70f)

var walls = listOf(wall0, wall1, wall2, wall3)

// Übergang von einem Rechteck zum anderen hakelig