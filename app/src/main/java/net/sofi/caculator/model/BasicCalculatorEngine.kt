package net.sofi.caculator.model

import kotlin.math.*
import java.util.Stack
import net.objecthunter.exp4j.ExpressionBuilder
object BasicCalculatorEngine {

    fun calculate(expression: String): String {
        if(expression.contains("!")){
            return try {
                val number = expression.replace("!", "").toIntOrNull()
                if (number != null && number >= 0) factorial(number).toString() else "Error"
            }catch (e: Exception) {
                "Error"  // Nếu có lỗi trong tính toán, trả về "Error"
            }

        }
        val sanitizedExpression = sanitizeExpression(expression)
        return try {
            val result = ExpressionBuilder(sanitizedExpression)
                .build()
                .evaluate() // Tính toán kết quả biểu thức
            result.toString()  // Trả về kết quả dưới dạng chuỗi
        } catch (e: Exception) {
            "Error"  // Nếu có lỗi trong tính toán, trả về "Error"
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
        sanitized = expression.replace("√", "sqrt")
        sanitized = sanitized.replace("log", "log10")
        //sanitized = sanitized.replace("x^2", "pow")

        return sanitized
    }
    private fun factorial(n: Int): Int {
        return if (n == 0) 1 else n * factorial(n - 1)
    }

    private fun evaluateExpression(expression: String): Double {
        // Sử dụng hàm eval() hoặc viết logic tính toán thủ công tùy vào yêu cầu.
        // Cẩn thận với eval(), nếu bạn xử lý theo cách thủ công thì nên viết một bộ phân tích cú pháp.
        // Giả sử ở đây bạn sẽ sử dụng thư viện hỗ trợ hoặc tự viết hàm để tính toán.
        return try {
            // Tính toán biểu thức (bạn có thể thay thế bằng cách phân tích cú pháp biểu thức)
            // Cần cẩn thận với các phép toán phức tạp như log và căn bậc hai
            val result = ExpressionBuilder(expression).build().evaluate() // Sử dụng thư viện ExpressionBuilder
            result
        } catch (e: Exception) {
            0.0 // Nếu có lỗi trong tính toán, trả về 0.0
        }
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