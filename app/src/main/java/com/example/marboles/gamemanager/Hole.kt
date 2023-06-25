package com.example.marboles.gamemanager

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// TODO: Loch Composable für Game Over Condition

@Composable
fun HoleView() {
    for (hole in holes) {
        Canvas(modifier = Modifier) {
            drawCircle(
                color = Color.Red,
                radius = hole.radius.dp.toPx(),
                center = Offset(x = hole.centerX.dp.toPx(), y = hole.centerY.dp.toPx())
            )
        }
    }
}