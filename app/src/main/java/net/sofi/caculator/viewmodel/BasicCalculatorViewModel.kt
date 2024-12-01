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
            "√" -> {
                // Xử lý phép toán căn bậc hai
                _uiState.value = BasicCalculatorResultState(display = currentDisplay + "√")
            }
            "log" -> {
                // Xử lý phép toán logarit
                _uiState.value = BasicCalculatorResultState(display = currentDisplay + "log")
            }

            "x^2" -> {
                val number = currentDisplay.toDoubleOrNull()
                if (number != null) {
                    _uiState.value =
                        BasicCalculatorResultState(display = (currentDisplay+"^2").toString())
                } else {
                    _uiState.value = BasicCalculatorResultState(display = "Error")
                }
            }
            "x!" -> {
                val number = currentDisplay.toIntOrNull()
                if (number != null && number >= 0) {
                    _uiState.value = BasicCalculatorResultState(display = (currentDisplay+"!").toString())
                } else {
                    _uiState.value = BasicCalculatorResultState(display = "Error")
                }
            }
            else -> {
                _uiState.value = BasicCalculatorResultState(display = currentDisplay + value)
            }
        }
    }
}
private fun factorial(n: Int): Int {
    return if (n == 0) 1 else n * factorial(n - 1)
}
