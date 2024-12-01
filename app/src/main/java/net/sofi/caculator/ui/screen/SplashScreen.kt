package net.sofi.caculator.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import net.sofi.caculator.R
import net.sofi.caculator.ui.navigation.Home
import net.sofi.caculator.ui.navigation.Splash

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(navController: NavController) {
    // Animation for fade-in and scale-up
    val scale = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1500) // 1.5s for scale effect
    )

    val alpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1500) // 1.5s for fade-in effect
    )

    // Loading progress animation from 0 to 100%
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        // Simulate loading progress
        for (i in 0..100) {
            progress = i / 100f
            delay(30) // Update progress every 30ms
        }
        // After the loading finishes, navigate to the main screen
        delay(500) // Small delay to ensure the progress bar is fully filled
        navController.navigate(Home.route) {
            popUpTo(Splash.route) { inclusive = true }
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set background color to white
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        )
        {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Your main screen content, for now just a Text
                Text("Welcome to the Main Screen",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Logo with fade-in and scale-up effect
                    Image(
                        painter = painterResource(id = R.drawable.icon), // Replace with your logo
                        contentDescription = "Splash Logo",

                        modifier = Modifier
                            .size(200.dp) // Set initial logo size
                            .scale(scale.value) // Apply scale-up effect
                            .alpha(alpha.value) // Apply fade-in effect
                            .clip(RoundedCornerShape(32.dp))
                    )

                    Spacer(modifier = Modifier.height(30.dp)) // Space between logo and progress bar

                    // Loading progress bar
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Set width of the progress bar
                            .height(6.dp), // Set height of the progress bar
                        color = MaterialTheme.colorScheme.primary, // Customize color
                        trackColor = Color.Gray.copy(alpha = 0.3f) // Track color (background of progress bar)
                    )
                }
            }
        }


        }



}

@Composable
fun MainScreen() {
    // Your main screen content
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Your main screen content, for now just a Text
        Text("Welcome to the Main Screen")
    }
}


