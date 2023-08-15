package com.example.marboles.mvvm.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marboles.R


@Composable
fun WoodImage() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.wood),
        contentDescription = "Holzhintergrund",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MenuTitle(label : String) {
    Text(text = label.uppercase(), color = Color.Black, fontSize = 40.sp, letterSpacing = 10.sp)
}

@Composable
fun NavigationButton(label : String, onClick: () -> Unit){

    Button(
        onClick = onClick, // wird übergeben
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent))

    {
        Text(text = label, color = Color.Black, fontSize = 30.sp)
    }
}
