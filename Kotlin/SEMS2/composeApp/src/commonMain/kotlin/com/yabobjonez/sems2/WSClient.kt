package com.yabobjonez.sems2

import androidx.compose.runtime.MutableState
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class WSClient(
    var isConnected: MutableState<Boolean>,
    var isActive: MutableState<Boolean>,
    var isButtonDown: MutableState<Boolean>
) {
    private val player = AudioPlayer()
    private val client = HttpClient(CIO) {
        install(WebSockets)
    }
    private var session: DefaultClientWebSocketSession? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    fun connect(ip: String) {
        scope.launch {
            try {
                session = client.webSocketSession(
                    host = ip,
                    port = 8080,
                    path = "/alarm"
                )
                isConnected.value = true

                session!!.incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        when (frame.readText()) {
                            "FIRE" -> {
                                isActive.value = true
                                player.playAlarm()
                            }
                            "mute" -> player.stop()
                            "clear" -> {
                                isActive.value = false
                                player.playAllClear()
                            }
                            "button_status?" -> {
                                val statusMessage = if (isButtonDown.value) "down" else "up"
                                session!!.send(Frame.Text("button_status = $statusMessage"))
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                isConnected.value = false
                isButtonDown.value = false
                session = null
            }
        }
    }

    fun toggleButton() {
        isButtonDown.value = !isButtonDown.value
        if (isButtonDown.value) {
            scope.launch {
                session?.send(Frame.Text("FIRE"))
            }
        }
    }

    fun disconnect() {
        scope.launch {
            session?.close(CloseReason(CloseReason.Codes.NORMAL, "Disconnect request"))
        }
    }
}
