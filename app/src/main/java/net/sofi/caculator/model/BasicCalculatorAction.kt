package net.sofi.caculator.model

sealed  class BasicCalculatorAction {
    data class ButtonClick(val value: String) : BasicCalculatorAction()
}
