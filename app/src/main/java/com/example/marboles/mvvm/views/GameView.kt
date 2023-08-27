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
import com.example.marboles.mvvm.*

import com.example.marboles.mvvm.viewModels.ScoreGameViewModel
import com.example.marboles.mvvm.viewModels.SensorViewModel


///////////////
// GAMEFIELD //
///////////////

@Composable
fun BallScreen(
    sensorViewModel: SensorViewModel,
    gameViewModel: ScoreGameViewModel,
    onClickHome: () -> Unit,
    onClickScore: () -> Unit,
    onClickGame: () -> Unit,
    levelNumber: Int
) {

    val ballCoordinates by sensorViewModel.ballCoordinates.observeAsState(Offset.Zero)

    Box(modifier = Modifier.zIndex(100f)) {
        TopBar(gameViewModel, sensorViewModel, onClickHome, onClickScore, onClickGame)
    }

    // Playing Field = Screen Size
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {

        when (levelNumber) {
            1 -> {
                HoleView(levelNumber)
                GoalView(levelNumber)
                WallView(levelNumber)
            }

            2 -> {
                HoleView(levelNumber)
                GoalView(levelNumber)
                WallView(levelNumber)
            }

            3 -> {
                HoleView(levelNumber)
                GoalView(levelNumber)
                WallView(levelNumber)
            }

            4 -> {
                HoleView(levelNumber)
                GoalView(levelNumber)
                WallView(levelNumber)
            }

            5 -> {
                HoleView(levelNumber)
                GoalView(levelNumber)
                WallView(levelNumber)
            }

            else -> throw Exception("Level kann nicht gebaut werden... Weil es dieses noch nicht gibt")
        }
        Ball(Modifier, ballCoordinates)
        Gameborders()
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
fun GoalView(levelNumber : Int) {
    when(levelNumber){
        1 -> {
            Image(
                modifier = Modifier
                    .offset(x = levelOneGoal.centerX.dp, y = levelOneGoal.centerY.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Ziel",
                contentScale = ContentScale.Fit
            )
        }

        2 -> {
            Image(
                modifier = Modifier
                    .offset(x = levelTwoGoal.centerX.dp, y = levelTwoGoal.centerY.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Ziel",
                contentScale = ContentScale.Fit
            )
        }

        3 -> {
            Image(
                modifier = Modifier
                    .offset(x = levelThreeGoal.centerX.dp, y = levelThreeGoal.centerY.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Ziel",
                contentScale = ContentScale.Fit
            )
        }

        4 -> {
            Image(
                modifier = Modifier
                    .offset(x = levelFourGoal.centerX.dp, y = levelFourGoal.centerY.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Ziel",
                contentScale = ContentScale.Fit
            )
        }

        5 -> {
            Image(
                modifier = Modifier
                    .offset(x = levelFiveGoal.centerX.dp, y = levelFiveGoal.centerY.dp)
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Ziel",
                contentScale = ContentScale.Fit
            )
        }

        else -> throw Exception("Ziel fehlgeschlagen. Dieses Level existiert noch nicht ;)")
    }
}

@Composable
fun HoleView(levelNumber : Int) {
    when(levelNumber){
        1 -> {
            for (hole in holesLevelOne) {
                Image(
                    modifier = Modifier
                        .offset(x = hole.centerX.dp, y = hole.centerY.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.hole),
                    contentDescription = "Loch",
                    contentScale = ContentScale.Fit
                )
            }
        }

        2 -> {
            for (hole in holesLevelTwo) {
                Image(
                    modifier = Modifier
                        .offset(x = hole.centerX.dp, y = hole.centerY.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.hole),
                    contentDescription = "Loch",
                    contentScale = ContentScale.Fit
                )
            }
        }

        3 -> {
            for (hole in holesLevelThree) {
                Image(
                    modifier = Modifier
                        .offset(x = hole.centerX.dp, y = hole.centerY.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.hole),
                    contentDescription = "Loch",
                    contentScale = ContentScale.Fit
                )
            }

        }

        4 -> {
            for (hole in holesLevelFour) {
                Image(
                    modifier = Modifier
                        .offset(x = hole.centerX.dp, y = hole.centerY.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.hole),
                    contentDescription = "Loch",
                    contentScale = ContentScale.Fit
                )
            }
        }

        5 -> {
            for (hole in holesLevelFive) {
                Image(
                    modifier = Modifier
                        .offset(x = hole.centerX.dp, y = hole.centerY.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.hole),
                    contentDescription = "Loch",
                    contentScale = ContentScale.Fit
                )
            }
        }

        else -> throw Exception("Löcher fehlgeschlagen. Dieses Level existiert noch nicht.")
    }

}

// WAND
@Composable
fun WallView(levelNumber : Int) {
    Canvas(modifier = Modifier) {
        when(levelNumber){
            1 -> {
                for (wall in wallsLevelOne){
                    drawRect(
                        color = Color(92, 64, 51),
                        size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                        topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
                    )
                }
            }

            2 -> {
                for (wall in wallsLevelTwo){
                    drawRect(
                        color = Color(92, 64, 51),
                        size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                        topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
                    )
                }
            }

            3 -> {
                for (wall in wallsLevelThree){
                    drawRect(
                        color = Color(92, 64, 51),
                        size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                        topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
                    )
                }
            }

            4 -> {
                for (wall in wallsLevelFour){
                    drawRect(
                        color = Color(92, 64, 51),
                        size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                        topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
                    )
                }
            }

            5 -> {
                for (wall in wallsLevelFive){
                    drawRect(
                        color = Color(92, 64, 51),
                        size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                        topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
                    )
                }
            }

            else -> throw Exception("Wände fehlgeschlagen. Dieses Level existiert noch nicht.")
        }

    }
}

@Composable
fun Gameborders() {
    Canvas(modifier = Modifier) {
        for (wall in borderwalls){
            drawRect(
                color = Color.White,
                size = Size(width = wall.wallRightX.dp.toPx() - wall.wallLeftX.dp.toPx(), height = wall.wallBottomY.dp.toPx() - wall.wallTopY.dp.toPx()),
                topLeft = Offset( x= wall.wallLeftX.dp.toPx(), y = wall.wallTopY.dp.toPx())
            )
        }
    }
}

///////////////
/// SCREENS ///
///////////////

@Composable
fun TopBar(
    gameViewModel: ScoreGameViewModel,
    sensorViewModel: SensorViewModel,
    onClickHome: () -> Unit,
    onClickScore: () -> Unit,
    onClickGame: () -> Unit
) {
    // Timer-logic ist letzt im game view model

    val gameState by sensorViewModel.gameState.observeAsState()
    // val isPaused by gameViewModel.isPaused.observeAsState(initial = false)

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
            TextButton(onClick = {
                gameViewModel.changePausedState()
                sensorViewModel.gameState.value = GameState.PAUSED
            }) {
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
        }
        Text(text = "Timer: ${gameViewModel.formatTimer(time)}", fontSize = 20.sp)
    }

    if (gameState == GameState.PAUSED) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 75.dp)
        ) {
            PauseOverlay(sensorViewModel, onClickHome, onClickGame)
        }
    }
}


@Composable
fun PauseOverlay(
    sensorViewModel: SensorViewModel,
    onClickHome: () -> Unit,
    onClickGame: () -> Unit
) {
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 30.dp, 0.dp, 10.dp)
                        ,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("PAUSE", fontSize = 40.sp, letterSpacing = 10.sp, color = Color.Black)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(

                        ) {
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
                    Row() {
                        Button(
                            modifier = Modifier
                                .padding(10.dp),
                            onClick = {
                                sensorViewModel.gameState.value = GameState.PAUSED
                                onClickHome()
                            }
                        ) {
                            Text(text = "Home", fontSize = 20.sp)
                        }

                        Button(
                            modifier = Modifier
                                .padding(10.dp),
                            onClick = {
                                onClickGame()
                            }
                        ) {
                            sensorViewModel.gameState.value = GameState.INGAME
                            Text(text = "Game", fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}


// Win Screen
@Composable
fun WinScreen(gameViewModel: ScoreGameViewModel, sensorViewModel: SensorViewModel, onClickHome: () -> Unit, onClickGame: () -> Unit) {

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
                        Spacer(modifier = Modifier.size(15.dp))
                        Text("Your Score:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            gameViewModel.formatTimer(),
                            fontSize = 30.sp,
                            color = Color(98, 0, 237, 255)
                        )
                        Spacer(modifier = Modifier.size(15.dp))

                        Row {
                            Button(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(80.dp)
                                    .padding(10.dp),
                                onClick = {
                                    sensorViewModel.gameState.value = GameState.PAUSED
                                    gameViewModel.resetTimer()
                                    sensorViewModel.resetGameState()
                                    // TODO: resetTimer und resetGamestate vereinen
                                    onClickHome()
                                }
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
                                    sensorViewModel.resetGameState()
                                    // TODO: resetTimer und resetGamestate vereinen
                                    onClickGame() // TODO : Next Level
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
fun GameOverScreen(gameViewModel: ScoreGameViewModel, onClickHome: () -> Unit, onClickGame: () -> Unit, sensorViewModel: SensorViewModel) {

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
                        Spacer(modifier = Modifier.size(15.dp))
                        Text("You wasted this much of your time:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            gameViewModel.formatTimer(),
                            fontSize = 30.sp,
                            color = Color(98, 0, 237, 255)
                        )
                        Spacer(modifier = Modifier.size(15.dp))

                        Row {
                            Button(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(80.dp)
                                    .padding(10.dp),
                                onClick = {
                                    sensorViewModel.resetGameState()
                                    onClickHome()
                                }
                            ) {
                                Text(text = "Home", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(modifier = Modifier
                                .width(150.dp)
                                .height(80.dp)
                                .padding(10.dp),
                                onClick = {
                                    sensorViewModel.gameState.value = GameState.INGAME
                                    // TODO : resetGameState() und resetTimer vereinen
                                    sensorViewModel.resetGameState()
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

