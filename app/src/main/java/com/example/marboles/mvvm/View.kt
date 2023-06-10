package com.example.marboles.mvvm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marboles.R

// GAMESCREEN
@Composable
fun BallScreen(navController: NavController, viewModel : SensorViewModel) {
    val ballCoordinates by viewModel.ballCoordinates.observeAsState(Offset.Zero)

    // Playing Field = Screen Size
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent))
    {
        TextButton(
            modifier = Modifier.align(Alignment.TopStart).size(70.dp),
            onClick = { navController.navigate("pause") }
        ) {
             Text(text = "ll", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }

        TextButton(
            modifier = Modifier.align(Alignment.TopEnd).size(70.dp),
            onClick = { navController.navigate("gameover") }
        ) {
            Text(text = "Kill", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Ball(Modifier, ballCoordinates)
        Wall(40f, 90f, 160f, 60f)
    }
}

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

// WAND
@Composable
fun Wall(left : Float, right : Float, top : Float, bottom : Float) {
    Canvas( modifier = Modifier ) {
        val leftX = left
        val rightX = right
        val topY = top
        val bottomY = bottom

        drawRect(
            color = Color.Black,
            size = Size(width = rightX - leftX, height = bottomY - topY),
            topLeft = Offset(x = rightX, y = topY)
        )
    }
}
