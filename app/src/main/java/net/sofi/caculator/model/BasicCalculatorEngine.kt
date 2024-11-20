package net.sofi.caculator.model


import java.util.Stack

object BasicCalculatorEngine {

    fun calculate(expression: String): String {
        return try {
            val sanitizedExpression = sanitizeExpression(expression)
            val result = evaluate(sanitizedExpression)
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun sanitizeExpression(expression: String): String {
        var sanitized = expression.trim()

        if (sanitized.startsWith("-")) {
            sanitized = "0$sanitized"
        }

        sanitized = sanitized.replace("--", "+")

        sanitized = sanitized.replace(Regex("([x/+-])\\+"), "$1")

        sanitized = sanitized.replace(" ", "")

        return sanitized
    }

    private fun evaluate(expression: String): Double {
        val values = Stack<Double>()
        val operators = Stack<Char>()
        var i = 0

        while (i < expression.length) {
            val char = expression[i]

            when {
                char.isDigit() || char == '.' -> {
                    val buffer = StringBuilder()
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        buffer.append(expression[i])
                        i++
                    }
                    values.push(buffer.toString().toDouble())
                    i--
                }
                char == '-' && (i == 0 || expression[i - 1] in listOf('+', '-', 'x', '/', '(')) -> {
                    val buffer = StringBuilder("-")
                    i++
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        buffer.append(expression[i])
                        i++
                    }
                    values.push(buffer.toString().toDouble())
                    i--
                }
                char == '(' -> operators.push(char)
                char == ')' -> {
                    while (operators.peek() != '(') {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.pop()
                }
                char in listOf('+', '-', 'x', '/') -> {
                    while (operators.isNotEmpty() && hasPrecedence(char, operators.peek())) {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.push(char)
                }
            }
            i++
        }

        while (operators.isNotEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == 'x' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }

    private fun applyOperator(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            'x' -> a * b
            '/' -> if (b != 0.0) a / b else throw ArithmeticException("Division by zero")
            else -> throw IllegalArgumentException("Unsupported operator: $op")
        }
    }
}