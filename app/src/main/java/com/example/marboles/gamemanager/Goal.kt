package com.example.marboles.gamemanager

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp

@Composable
fun Goal(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 250f, top = 200f) {
            drawCircle(Color.DarkGray, radius = 30.dp.toPx())
        }
    }
}