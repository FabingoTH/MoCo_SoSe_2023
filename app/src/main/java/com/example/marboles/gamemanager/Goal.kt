package com.example.marboles.gamemanager

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Goal(){
    Canvas( modifier = Modifier ) {
        drawCircle(
            color = Color.DarkGray, radius =60f, center=
            Offset(x = 160.dp.toPx(), y = 135.dp.toPx()))
    }
}