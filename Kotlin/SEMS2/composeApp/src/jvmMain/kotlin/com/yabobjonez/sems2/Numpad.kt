package com.yabobjonez.sems2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Numpad(
    enabled: Boolean,
    onSubmit: (String) -> Unit,
    onCancel: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    var currentInput by remember { mutableStateOf("") }

    fun onButtonClick(value: String) {
        when (value) {
            "<" -> currentInput = currentInput.dropLast(1);
            "⊘" -> {
                currentInput = ""
                onCancel()
            }
            "↵" -> {
                onSubmit(currentInput)
                currentInput = ""
            }
            else -> currentInput += value
        }
        onValueChange(currentInput)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        val numpadButtons = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("<", "0", "."),
            listOf("⊘", "↵"),
        )
        numpadButtons.forEach { row ->
            Row (
                modifier = Modifier.width(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                row.forEach { buttonText ->
                    Button(
                        onClick = { onButtonClick(buttonText) },
                        enabled = enabled,
                        modifier = Modifier.weight(if (buttonText == "↵") 2f else 1f),
                    ) {
                        Text(buttonText)
                    }
                }
            }
        }
    }
}
