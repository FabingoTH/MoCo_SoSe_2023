package com.example.marboles.gamemanager

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// TODO: Loch Composable für Game Over Condition

@Composable
fun Hole(centerX : Float, centerY : Float){
    Canvas( modifier = Modifier ) {
        drawCircle(
            color = Color.Red,
            radius = 30.dp.toPx(),
            center = Offset(x = centerX.dp.toPx(), y = centerY.dp.toPx())
        )
    }
}
