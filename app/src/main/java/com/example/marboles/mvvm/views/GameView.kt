package com.example.marboles.mvvm.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.marboles.GameState

import com.example.marboles.R
import com.example.marboles.mvvm.holes

import com.example.marboles.mvvm.viewModels.GameViewModel
import com.example.marboles.mvvm.viewModels.SensorViewModel


///////////////
// GAMEFIELD //
///////////////

@Composable
fun BallScreen(
    sensorViewModel: SensorViewModel,
    gameViewModel: GameViewModel,
    onClickHome: () -> Unit,
    onClickScore: () -> Unit,
    onClickWin: () -> Unit
) {

    val ballCoordinates by sensorViewModel.ballCoordinates.observeAsState(Offset.Zero)

    Box(modifier = Modifier.zIndex(100f)) {
        TopBar(gameViewModel, sensorViewModel, onClickHome, onClickScore, onClickWin)
    }

    // Playing Field = Screen Size
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        HoleView()
        Goal(160f, 135f)
        Ball(Modifier, ballCoordinates)
        WallsLevelOne()
    }
}

///////////////////
// GAME ELEMENTS //
///////////////////

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

@Composable
fun Goal(centerX: Float, centerY: Float) {
    Canvas(modifier = Modifier) {
        drawCircle(
            color = Color.DarkGray, radius = 30.dp.toPx(), center =
            Offset(x = centerX.dp.toPx(), y = centerY.dp.toPx())
        )
    }
}

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

// WAND
@Composable
fun WallsLevelOne() {
    Canvas(modifier = Modifier) {
        drawRect(
            color = Color.Black,
            size = Size(width = 50.dp.toPx(), height = 150.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 60.dp.toPx())
        )
        drawRect(
            color = Color.Red,
            size = Size(width = 250.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = 40.dp.toPx(), y = 10.dp.toPx())
        )
        drawRect(
            color = Color.Blue,
            size = Size(width = 290.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -200.dp.toPx(), y = -40.dp.toPx())
        )
        drawRect(
            color = Color.Green,
            size = Size(width = 50.dp.toPx(), height = 50.dp.toPx()),
            topLeft = Offset(x = -160.dp.toPx(), y = 70.dp.toPx())
        )
    }
}


///////////////
/// SCREENS ///
///////////////

@Composable
fun TopBar(
    gameViewModel: GameViewModel,
    sensorViewModel: SensorViewModel,
    onClickHome: () -> Unit,
    onClickScore: () -> Unit,
    onClickWin: () -> Unit
) {

    // Timer-logic ist letzt im game view model
    val isPaused by gameViewModel.isPaused.observeAsState(initial = false)
    val time by gameViewModel.time.observeAsState(initial = 0)

    LaunchedEffect(Unit) { // Coroutine starten
        gameViewModel.timer()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(50.dp, 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row() {
            TextButton(onClick = { gameViewModel.changePausedState() }) {
                Text(text = "ll", fontSize = 20.sp)
            }
            TextButton(
                onClick = {
                    sensorViewModel.gameState.value = GameState.PAUSED
                    onClickHome()
                }
            ) {
                Text(text = "Home", fontSize = 20.sp)
            }
            TextButton( // Ich denke mal der Button kommt bald wieder weg, deswegen kein State
                onClick = onClickScore
            ) {
                Text(text = "Highscore", fontSize = 20.sp)
            }
            TextButton(
                onClick = {
                    sensorViewModel.gameState.value = GameState.WON
                    onClickWin()
                }
            ) {
                Text(text = "Win", fontSize = 20.sp)
            }

        }
        Text(text = "Timer: ${gameViewModel.formatTimer(time)}", fontSize = 20.sp)
    }

    if (isPaused) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 75.dp)
        ) {
            PauseOverlay()
        }
    }
}


@Composable
fun PauseOverlay() {

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
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 30.dp, 0.dp),
                                    text = "SFX", fontSize = 30.sp
                                )
                                val checkedState = remember { mutableStateOf(true) }
                                Switch(
                                    checked = checkedState.value,
                                    onCheckedChange = { checkedState.value = it }
                                )
                            }
                            Row(
                                Modifier.fillMaxWidth(0.4f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(0.dp, 0.dp, 30.dp, 0.dp),
                                    text = "Music", fontSize = 30.sp
                                )
                                val checkedState = remember { mutableStateOf(true) }
                                Switch(
                                    checked = checkedState.value,
                                    onCheckedChange = { checkedState.value = it }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// Win Screen
@Composable
fun WinScreen(gameViewModel: GameViewModel, onClickHome: () -> Unit, onClickGame: () -> Unit) {

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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MenuTitle(label = "You Win")
                        Text("Your Score:", fontSize = 20.sp)
                        Spacer(modifier = Modifier)
                        Text(
                            gameViewModel.formatTimer(),
                            fontSize = 30.sp,
                            color = Color(98, 0, 237, 255)
                        )
                        Spacer(modifier = Modifier)

                        Row {
                            Button(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(80.dp)
                                    .padding(10.dp),
                                onClick = {
                                    gameViewModel.resetTimer()
                                    onClickHome
                                }
                            ) {
                                Text(text = "Home", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                                onClick = {
                                    gameViewModel.resetTimer()
                                    onClickGame()
                                }
                            ) {
                                Text(
                                    text = "Next",
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// für den game over screen muss die zeit noch pausiert werden im vm (isPaused ändern)
@Composable
fun GameOverScreen(gameViewModel: GameViewModel, onClickHome: () -> Unit, onClickGame: () -> Unit, sensorViewModel: SensorViewModel) {

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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp, 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MenuTitle(label = "Game Over!")
                        Text("You wasted this much of your time:", fontSize = 20.sp)
                        Spacer(modifier = Modifier)
                        Text(
                            gameViewModel.formatTimer(),
                            fontSize = 30.sp,
                            color = Color(98, 0, 237, 255)
                        )
                        Spacer(modifier = Modifier)

                        Row {
                            Button(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(80.dp)
                                    .padding(10.dp),
                                onClick = onClickHome
                            ) {
                                Text(text = "Home", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                                onClick = {
                                    sensorViewModel.gameState.value = GameState.INGAME
                                    gameViewModel.resetTimer()
                                    onClickGame()
                                }
                            ) {
                                Text(
                                    text = "Retry",
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

