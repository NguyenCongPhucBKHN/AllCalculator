package net.sofi.caculator.model


import java.util.Stack

object BasicCalculatorEngine {

    // Hàm chính tính toán biểu thức
    fun calculate(expression: String): String {
        return try {
            val sanitizedExpression = sanitizeExpression(expression)
            val result = evaluate(sanitizedExpression)
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    // Hàm xử lý và chuẩn hóa biểu thức đầu vào
    private fun sanitizeExpression(expression: String): String {
        var sanitized = expression.trim()

        // Xử lý số âm ở đầu chuỗi (ví dụ: "-5+2")
        if (sanitized.startsWith("-")) {
            sanitized = "0$sanitized"
        }

        // Xử lý hai dấu trừ liên tiếp (-- thành +)
        sanitized = sanitized.replace("--", "+")

        // Loại bỏ dấu `+` dư thừa sau các toán tử
        sanitized = sanitized.replace(Regex("([x/+-])\\+"), "$1")

        // Loại bỏ khoảng trắng
        sanitized = sanitized.replace(" ", "")

        return sanitized
    }

    // Hàm tính toán biểu thức đã chuẩn hóa
    private fun evaluate(expression: String): Double {
        val values = Stack<Double>()
        val operators = Stack<Char>()
        var i = 0

        while (i < expression.length) {
            val char = expression[i]

            when {
                // Nếu là số hoặc dấu thập phân, xử lý thành số
                char.isDigit() || char == '.' -> {
                    val buffer = StringBuilder()
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        buffer.append(expression[i])
                        i++
                    }
                    values.push(buffer.toString().toDouble())
                    i--
                }
                // Nếu là toán tử (-) ngay sau một toán tử khác hoặc đầu chuỗi, hiểu là số âm
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
                // Nếu là dấu mở ngoặc
                char == '(' -> operators.push(char)
                // Nếu là dấu đóng ngoặc, xử lý toàn bộ bên trong dấu ngoặc
                char == ')' -> {
                    while (operators.peek() != '(') {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.pop() // Bỏ dấu '('
                }
                // Nếu là toán tử (+, -, *, /)
                char in listOf('+', '-', 'x', '/') -> {
                    while (operators.isNotEmpty() && hasPrecedence(char, operators.peek())) {
                        values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.push(char)
                }
            }
            i++
        }

        // Thực hiện các phép toán còn lại
        while (operators.isNotEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    // Kiểm tra độ ưu tiên của toán tử
    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == 'x' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }

    // Áp dụng toán tử lên hai toán hạng
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