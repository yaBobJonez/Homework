package com.yabobjonez.sems2

import HoldButton
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val sm = remember { StateManager() }

    Window(
        onCloseRequest = {
            sm.server.stop()
            sm.player.release()
            exitApplication()
        },
        title = "SEMS2"
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Numpad(
                    enabled = sm.numpadEnabled,
                    onSubmit = { sm.performAction() },
                    onCancel = { sm.cancelAction() },
                    onValueChange = { sm.displayInput = it }
                )
                Column (
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        value = sm.displayText,
                        placeholder = { Text(sm.displayPlaceholder) },
                        readOnly = true,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 8.dp)
                    )
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { sm.transition(State.ACKNOWLEDGED) }) {
                            Text("Acknowledge")
                        }
                        Button(onClick = { sm.transition(State.SILENCED) }) {
                            Text("Silence")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        HoldButton(onLongClick = {
                            sm.alarmCause = "triggered manually"
                            sm.transition(State.FIRE)
                        }) {
                            Text("Drill")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        HoldButton(onLongClick = { sm.transition(State.NORMAL) }) {
                            Text("Reset")
                        }
                    }
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(onClick = { sm.transition(State.NORMAL) }) {
                        Text("Arm")
                    }
                    HoldButton(onLongClick = { sm.transition(State.DISARMED) }) {
                        Text("Disarm")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { sm.transition(State.DISCONNECTING) }) {
                        Text("Disconnect")
                    }
                    Button(onClick = { sm.transition(State.BLOCKING) }) {
                        Text("Block")
                    }
                    Button(onClick = { sm.transition(State.UNBLOCKING) }) {
                        Text("Unblock")
                    }
                }
                ClientList(sm.server.model)
            }
        }
    }
}
