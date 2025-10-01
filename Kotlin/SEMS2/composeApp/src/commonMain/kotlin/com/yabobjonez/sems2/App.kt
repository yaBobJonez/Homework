package com.yabobjonez.sems2

import HoldButton
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App() {
    val isConnected = remember { mutableStateOf(false) }
    val isActive = remember { mutableStateOf(false) }
    val isButtonDown = remember { mutableStateOf(false) }
    val client = remember { WSClient(isConnected, isActive, isButtonDown) }
    var serverIp by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition()
    val flashColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 150, delayMillis = 600),
            repeatMode = RepeatMode.Restart
        )
    )
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "FIRE",
            color = Color.White,
            fontSize = 70.sp,
            modifier = Modifier.padding(16.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(16.dp)
                .background(
                    color = if (isActive.value) flashColor else Color.DarkGray,
                    shape = RoundedCornerShape(24.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(24.dp))
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp)
                .background(
                    color = if (isButtonDown.value) Color.DarkGray else Color.White,
                    shape = RoundedCornerShape(24.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(24.dp))
                .combinedClickable(
                    enabled = isConnected.value,
                    onClick = {},
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        client.toggleButton()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isButtonDown.value) "Reset" else ">> HOLD <<",
                color = if (isButtonDown.value) Color.LightGray else Color.Black,
                fontSize = 50.sp
            )
        }
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            if (isConnected.value) {
                HoldButton (
                    onLongClick = { client.disconnect() },
                ) {
                    Text("Disconnect")
                }
            } else {
                TextField (
                    value = serverIp,
                    onValueChange = { serverIp = it },
                    singleLine = true,
                    label = { Text("Server IP") },
                    placeholder = { Text("127.0.0.1") },
                    modifier = Modifier.weight(1f)
                )
                Button (
                    onClick = { client.connect(serverIp) },
                ) {
                    Text("Connect")
                }
            }
        }
    }
}