package com.example.marboles.gamemanager

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// WAND
@Composable
fun WallView() {
    Canvas( modifier = Modifier ) {
        drawRect(
            color = Color.Black,
            size = Size(width = 50.dp.toPx(), height = 150.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 60.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 250.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 10.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 290.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -200.dp.toPx(), y = -40.dp.toPx())
        )
        drawRect(
            color = Color.Black,
            size = Size(width = 50.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -160.dp.toPx(), y = 70.dp.toPx())
        )
    }
}