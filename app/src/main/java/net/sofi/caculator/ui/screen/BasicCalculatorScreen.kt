package net.sofi.caculator.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.sofi.caculator.ui.compose.BasicCalculator.BasicCalculatorButtons
import net.sofi.caculator.ui.compose.BasicCalculator.BasicCalculatorDisplay
import net.sofi.caculator.viewmodel.BasicCalculatorViewModel

@Composable
fun BasicCalculatorScreen(viewModel: BasicCalculatorViewModel) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        BasicCalculatorDisplay(displayText = state.value.display)
        Spacer(modifier = Modifier.height(16.dp))
        BasicCalculatorButtons(onAction = viewModel::onAction)
    }
}
