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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.example.marboles.ui.theme.MarbolesTheme
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.marboles.database.Highscore
import com.example.marboles.database.HighscoreDao
import com.example.marboles.database.HighscoreDatabase
import com.example.marboles.mvvm.*
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    // Datenbank Zeugs
    suspend fun getAllHighscores(dao : HighscoreDao) : List<Highscore>{
        var allHighscores = listOf<Highscore>()
        withContext(Dispatchers.IO) {
            val highscores = dao.getHighscoresByHighest()
            allHighscores = highscores
        }
        return allHighscores
    }

    suspend fun addHighscore(highscore : Highscore, dao : HighscoreDao) {
        withContext(Dispatchers.IO) {
            dao.insertHighscore(highscore)
        }
    }

    suspend fun deleteAllHighscores(dao : HighscoreDao, highscores : List<Highscore>){
        withContext(Dispatchers.IO) {
            dao.deleteHighscores(highscores)
        }
    }

    /*
    suspend fun addSampleHighscore(dao : HighscoreDao) {
        val highscore = Highscore(date = "10.06.2023", score = 30)
        withContext(Dispatchers.IO) {
            dao.insertHighscore(highscore)
        }
    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = SensorViewModel(this) // Funktioniert das...?
        val levelViewModel = LevelViewModel(this)

        val db = Room.databaseBuilder(
            applicationContext,
            HighscoreDatabase::class.java, "highscore"
        ).build()

        val highscoreDao = db.highscoreDao()
        var highscores = listOf<Highscore>()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                highscores = highscoreDao.getHighscoresByHighest()
            }
        }

        // FORCE FULLSCREEN
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // LOCK SCREEN TO LANDSCAPE MODE
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

            MarbolesTheme {
                val navController = rememberNavController()
                WoodImage()

                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen() }
                        composable("score") { ScoreScreen(highscores) }
                        composable("level") { LevelChoiceScreen(levelViewModel) }
                        composable("pause") {
                            PauseScreen(
                                remember { mutableStateOf(false) })
                        }
                        composable("game") { BallScreen(viewModel) }
                        composable("gameover") { GameOverScreen() }
                    }
                }
            }
        }
    }
}

// Init Local NavController
val LocalNavController = staticCompositionLocalOf<NavController?> { null }

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
fun HomeScreen() {

    val navController = LocalNavController.current

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
               NavigationButton(label = "Play", navController,"game")
               NavigationButton(label = "Level", navController, "level")
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
fun NavigationButton(label : String,navController: NavController?, screenName: String){

    Button(
        onClick = {navController?.navigate(screenName)},
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent))

    {
        Text(text = label, color = Color.Black, fontSize = 30.sp)
    }
}

// SCORE
@Composable
fun ScoreScreen ( highscoreList : List<Highscore>) {

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
                        if(highscoreList.isEmpty()){
                            ScoreEntry(datum = "Falsch", score = "Belegh")
                        } else {
                            for(element in highscoreList){
                                ScoreEntry(datum = element.date, score = element.score.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreEntry(datum: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = datum, color = Color.Black, fontSize = 30.sp)
        Text(text = score.toString(), color = Color.Black, fontSize = 30.sp)
    }
}

// Levelauswahl
@Composable
fun LevelChoiceScreen (levelViewModel: LevelViewModel) {
    val levelStatusList by levelViewModel.levelStatusList.observeAsState(emptyList())

    val navController = LocalNavController.current


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
                        levelStatusList.forEach { levelStatus ->
                            LevelButton(
                                levelStatus = levelStatus,
                                onLevelClicked = { /* Implementieren Sie die Logik für den Klick auf einen Level-Button */ }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LevelButton(
    levelStatus: LevelStatus,
    onLevelClicked: () -> Unit
) {
    val textColor = if (levelStatus.isUnlocked) Color.Black else Color.LightGray
    TextButton(
        onClick = onLevelClicked,
        modifier = Modifier,
        enabled = levelStatus.isUnlocked
    ) {
        Text(
            text = "${levelStatus.levelNumber}",
            fontSize = 20.sp,
            color = textColor
        )
    }
}

@Composable
fun MenuTitle(label : String) {
    Text(text = label.uppercase(), color = Color.Black, fontSize = 40.sp, letterSpacing = 10.sp)
}


@Composable
fun TopBar() {

    val navController = LocalNavController.current

    // Timer-logic
    val isPaused = remember { mutableStateOf(false) }
    val time = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) { // Coroutine starten
        while (true) {
            delay(1000) // Verzögerung von 1 Sekunde

            if (!isPaused.value) {
                time.value++
            }
        }
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
            TextButton(onClick = { isPaused.value = !isPaused.value }) {
                Text(text = "ll", fontSize = 20.sp,)
            }
            TextButton(onClick = {navController?.navigate("home") }) {
                Text(text = "Home", fontSize = 20.sp)
            }
            TextButton(onClick = { navController?.navigate("score") }
            ) {
                Text(text = "Highscore", fontSize = 20.sp)
            }
            TextButton(onClick = { navController?.navigate("gameover") }
            ) {
                Text(text = "Kill", fontSize = 20.sp)
            }
        }
        Text(text = "Timer: ${formatTimer(time.value)}", fontSize = 20.sp)
    }

    if (isPaused.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PauseScreen(isPaused = isPaused)
        }
    }
}

@Composable
fun PauseScreen(isPaused: MutableState<Boolean>) {

    val navController = LocalNavController.current

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
                       }
                    }
                }
            }
        }
    }
}

@Composable
fun GameOverScreen() {
    val navController = LocalNavController.current

    Row (
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
                Box (
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
                        Text("Your score", fontSize = 30.sp)
                        // TODO: Hier kommt der Timer rein
                        Text("")
                        Text("Sample Time", fontSize = 20.sp, color = Color(98,0,237,255))
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