package com.yabobjonez.sems2

import HoldButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import chaintech.videoplayer.host.MediaPlayerError
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.ui.audio.AudioPlayer
import chaintech.videoplayer.ui.audio.AudioPlayerComposable
import kotlinx.coroutines.delay
import sems2.composeapp.generated.resources.Res

fun main() = application {
    val clients = remember { mutableStateListOf<Client>() }

    val server = remember { WSServer(clients) }
    val player = remember { MediaPlayerHost(
        "http://localhost:8080/alarmData/alarm.mp3",
        isPaused = true
    )}
    val sm = remember { StateManager(server, clients) }
    LaunchedEffect(Unit) {
        server.start()
        player.play()
    }

    AudioPlayer(player)
    Window(
        onCloseRequest = ::exitApplication,
        title = "SEMS2",
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
                        Button(onClick = { exitApplication() }) {
                            Text("Acknowledge")
                        }
                        Button(onClick = { exitApplication() }) {
                            Text("Silence")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        HoldButton(onLongClick = { server.trigger() }) {
                            Text("Drill")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        HoldButton(onLongClick = { server.reset() }) {
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
                    Button(onClick = { exitApplication() }) {
                        Text("Arm")
                    }
                    HoldButton(onLongClick = { exitApplication() }) {
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
                ClientList(clients)
            }
        }
    }
}
