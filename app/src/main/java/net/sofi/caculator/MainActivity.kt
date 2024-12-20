package net.sofi.caculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.sofi.caculator.ui.screen.BasicCalculatorScreen
import net.sofi.caculator.ui.theme.CaculatorTheme
import net.sofi.caculator.viewmodel.BasicCalculatorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel // Để sử dụng viewModel() trong Compose
import androidx.navigation.compose.rememberNavController
import net.sofi.caculator.ui.navigation.NavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavigationGraph(navController = navController)
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaculatorTheme {
        Greeting("Android")
    }
}