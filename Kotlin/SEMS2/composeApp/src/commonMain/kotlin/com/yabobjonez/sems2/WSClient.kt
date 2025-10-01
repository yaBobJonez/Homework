package com.yabobjonez.sems2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class WSClient(
    var isConnected: MutableState<Boolean>,
    var isActive: MutableState<Boolean>,
    var isButtonDown: MutableState<Boolean>
) {
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
                            "FIRE" -> isActive.value = true
                            "clear" -> isActive.value = false
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
            session?.close(CloseReason(CloseReason.Codes.NORMAL, "Client disconnected"))
        }
    }
}
