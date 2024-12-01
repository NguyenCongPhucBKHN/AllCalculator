package net.sofi.caculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.sofi.caculator.ui.screen.BasicCalculatorScreen
import net.sofi.caculator.ui.screen.HomeScreen
import net.sofi.caculator.ui.screen.HomeScreen
import net.sofi.caculator.ui.screen.SplashScreen
import net.sofi.caculator.ui.theme.CaculatorTheme
import net.sofi.caculator.viewmodel.BasicCalculatorViewModel

@Composable
fun NavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Splash.route
    ) {
        composable(Splash.route)
        {
            SplashScreen(navController = navController)
        }
        composable(Home.route) {
            HomeScreen(navController = navController)
        }
        composable(BasicCalCulator.route) {
            CaculatorTheme {
                // A surface container using the 'background' color from the theme
                val viewModel: BasicCalculatorViewModel = viewModel()
                BasicCalculatorScreen(viewModel)
            }
        }


    }
}