package com.example.marboles

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marboles.ui.theme.MarbolesTheme
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // LOCK SCREEN TO LANDSCAPE MODE
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

            MarbolesTheme {

                WoodImage()
                //Homescreen()
                //ScoreScreen()
                //LevelChoiceScreen()
                PauseScreen()
            }
        }
    }

}

@Composable
fun WoodImage() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.wood),
        contentDescription = "Holzhintergrund",
        contentScale = ContentScale.Crop
    )
}

// HOME
@Composable
fun Homescreen() {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(title = "Marboles")
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(60.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
               SimpleButton(label = "Play")
               SimpleButton(label = "Level")
            }
        }
    }
}



@Composable
fun TitleText(title : String) {
    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(100.dp))
            .padding(50.dp, 10.dp)
    ) {
        Text(
            title.uppercase(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 20.sp
            )
        )
    }
}

@Composable
fun SimpleButton(label : String){
    Button(
        onClick = {},
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent))

    {
        Text(text = label, color = Color.Black, fontSize = 30.sp)
    }
}

// SCORE

@Composable
fun ScoreScreen () {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 30.dp, 0.dp, 50.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MenuTitle(label = "Highscores")
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        ScoreEntry(spielerName = "Anouk", score = "00:30")
                        ScoreEntry(spielerName = "Phit", score = "01:40")
                        ScoreEntry(spielerName = "Fabian", score = "02:00")
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreEntry(spielerName: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = spielerName, color = Color.Black, fontSize = 30.sp)
        Text(text = score.toString(), color = Color.Black, fontSize = 30.sp)
    }
}

// Levelauswahl
@Composable
fun LevelChoiceScreen () {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 30.dp, 0.dp, 50.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MenuTitle(label = "Level")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(

                            buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                )
                            ) {
                                append("1 ")
                            }
                            append("2 3 4 5 6")

                        },
                            fontSize = 30.sp,
                            color = Color.Gray,
                            letterSpacing = 15.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuTitle(label : String) {
    Text(text = label.uppercase(), color = Color.Black, fontSize = 40.sp, letterSpacing = 10.sp)
}


@Composable
fun PauseScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp, 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Text(text = "ll", fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Level", fontSize = 20.sp)
                }
                Text(text = "Time: 00:49", fontSize = 20.sp)
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(1f),
                shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 30.dp, 0.dp, 40.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MenuTitle(label = "Pause")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                      Column() {
                          Row(
                              Modifier.fillMaxWidth(0.4f),
                              horizontalArrangement = Arrangement.SpaceBetween,
                              verticalAlignment =  Alignment.CenterVertically

                          ) {
                              Text(
                                  modifier = Modifier
                                      .padding(0.dp, 0.dp, 30.dp,0.dp),
                                  text = "SFX", fontSize = 30.sp)
                              val checkedState = remember { mutableStateOf(true) }
                              Switch(
                                  checked = checkedState.value,
                                  onCheckedChange = { checkedState.value = it }
                              )
                          }
                          Row(
                              Modifier.fillMaxWidth(0.4f),
                              horizontalArrangement = Arrangement.SpaceBetween,
                              verticalAlignment =  Alignment.CenterVertically

                          ) {
                              Text(
                                  modifier = Modifier
                                      .padding(0.dp, 0.dp, 30.dp,0.dp),
                                  text = "Music", fontSize = 30.sp)
                              val checkedState = remember { mutableStateOf(true) }
                              Switch(
                                  checked = checkedState.value,
                                  onCheckedChange = { checkedState.value = it }
                              )
                          }
                          //Text(text = "Exit", fontSize = 30.sp)
                      }
                    }
                }
            }
        }
    }
}


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current

    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}