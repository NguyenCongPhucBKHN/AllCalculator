@file:OptIn(ExperimentalMaterial3Api::class)

package net.sofi.caculator.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import net.sofi.caculator.model.FeatureButtonModel
import net.sofi.caculator.ui.compose.Splash.FeatureButton
import net.sofi.caculator.ui.navigation.BasicCalCulator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.sofi.caculator.R
import net.sofi.caculator.ui.navigation.Splash

@Composable
fun SplashScreen(navController: NavController) {
    val buttons = listOf(
        FeatureButtonModel("Basic", BasicCalCulator.route) to R.drawable.basiccalculate,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.fx,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.sum,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.fx,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.basiccalculate,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.ic_launcher_foreground,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.sum,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.ic_launcher_foreground,
        FeatureButtonModel("Chưa có", Splash.route) to R.drawable.fx
    )

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // Display 3 buttons per row
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(buttons.size) { index ->
                    val (button, iconResID )= buttons[index]
                    FeatureButton(button = button, iconResId = iconResID, onClick = {
                        navController.navigate(button.route)
                    })
                }
            }
        }
    }
}