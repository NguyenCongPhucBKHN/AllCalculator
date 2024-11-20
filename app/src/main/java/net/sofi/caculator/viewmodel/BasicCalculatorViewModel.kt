package net.sofi.caculator.viewmodel



import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.sofi.caculator.model.BasicCalculatorAction
import net.sofi.caculator.model.BasicCalculatorEngine
import net.sofi.caculator.model.BasicCalculatorResultState


class BasicCalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BasicCalculatorResultState())
    val uiState: StateFlow<BasicCalculatorResultState> get() = _uiState

    fun onAction(action: BasicCalculatorAction) {
        when (action) {
            is BasicCalculatorAction.ButtonClick -> handleButtonClick(action.value)
        }
    }

    private fun handleButtonClick(value: String) {
        val currentDisplay = _uiState.value.display

        when (value) {
            "=" -> {
                val result = BasicCalculatorEngine.calculate(currentDisplay)
                _uiState.value = BasicCalculatorResultState(display = result)
            }
            "C" -> {
                // Clear all
                _uiState.value = BasicCalculatorResultState(display = "")
            }
            "⌫" -> {
                val newDisplay = if (currentDisplay.isNotEmpty()&&!currentDisplay.contains("Error")) {
                    currentDisplay.dropLast(1)
                }

                else ""
                _uiState.value = BasicCalculatorResultState(display = newDisplay)
            }
            else -> {
                _uiState.value = BasicCalculatorResultState(display = currentDisplay + value)
            }
        }
    }
}
