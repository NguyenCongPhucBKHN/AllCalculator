package net.sofi.caculator.ui.compose.BasicCalculator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.sofi.caculator.model.BasicCalculatorAction
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text

@Composable
fun BasicCalculatorButtons(onAction: (BasicCalculatorAction) -> Unit) {
    val buttons = listOf(
        listOf("C", "(", ")", "/"),
        listOf("7", "8", "9", "x"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "⌫", "=")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (button in row) {
                    val backgroundColor = when (button) {
                        "C" -> Color(0xFFFFC107) // Orange for clear
                        "⌫" -> Color(0xFFFFC107) // Orange for delete
                        "=" -> Color(0xFFFF5722) // Accent color for equal
                        else -> Color(0xFFECECEC) // Default light gray for numbers and operators
                    }
                    val textColor = if (button in listOf("C", "⌫", "=")) Color.White else Color.Black

                    Button(
                        onClick = { onAction(BasicCalculatorAction.ButtonClick(button)) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = backgroundColor,
                            contentColor = textColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = button,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}
