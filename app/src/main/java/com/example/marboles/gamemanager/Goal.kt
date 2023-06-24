package com.example.marboles.gamemanager

import android.util.Range
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
fun Goal(centerX : Float, centerY : Float){
    Canvas( modifier = Modifier ) {
        drawCircle(
            color = Color.DarkGray,
            radius = 28.dp.toPx(),
            center = Offset(x = centerX.dp.toPx(), y = centerY.dp.toPx()))
    }
}



