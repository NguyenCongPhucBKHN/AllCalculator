package net.sofi.caculator.ui.navigation

interface Destinations {
    val route: String
}
object Splash : Destinations {
    override val route = "Splash"
}


object BasicCalCulator : Destinations {
    override val route = "BasicCalculator"
}