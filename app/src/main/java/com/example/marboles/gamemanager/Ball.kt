package com.example.marboles.gamemanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marboles.R

// BALL
@Composable
fun Ball(modifier: Modifier = Modifier, coordinates : Offset) {
    Image(
        modifier = Modifier
            .offset(coordinates.x.dp, coordinates.y.dp)
            .size(50.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.marble),
        contentDescription = "Murmel",
        contentScale = ContentScale.Fit
    )
}