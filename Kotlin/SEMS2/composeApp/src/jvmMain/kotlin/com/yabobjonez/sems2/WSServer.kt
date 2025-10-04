package com.yabobjonez.sems2

import androidx.compose.runtime.mutableStateListOf
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

data class Client(
    val session: WebSocketSession,
    val ip: String
)

class WSServer(private val sm: StateManager) {
    val model = mutableStateListOf<Client>()
    val blockedIps = mutableSetOf<String>()
    val buttonStatusReplies = mutableListOf<Boolean>()
    private lateinit var instance: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>
    private val scope = CoroutineScope(Dispatchers.IO)

    fun start() {
        instance = embeddedServer(Netty, port = 8080) {
            install(WebSockets)

            routing {
                webSocket("/alarm") {
                    val ip = this.call.request.origin.remoteAddress
                    if (ip in blockedIps) {
                        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "IP address blocked"))
                        return@webSocket
                    }
                    val client = Client(
                        session = this,
                        ip = ip
                    )
                    model.add(client)

                    try {
                        incoming.consumeEach { frame ->
                            if (frame is Frame.Text) {
                                when (frame.readText()) {
                                    "FIRE" -> {
                                        sm.alarmCause = "triggered by $ip"
                                        sm.transition(State.FIRE)
                                    }
                                    "button_status = down" -> buttonStatusReplies.add(true)
                                    "button_status = up" -> buttonStatusReplies.add(false)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    } finally {
                        model.remove(client)
                    }
                }
            }
        }
        instance.start(wait = false)
    }

    fun stop() {
        instance.stop(1000, 5000)
    }

    suspend fun broadcast(message: String) {
        model.forEach { client ->
            try {
                client.session.send(Frame.Text(message))
            } catch (e: Exception) {
                println("Failed to send message '$message': ${e.message}")
            }
        }
    }

    fun disconnect(
        index: String,
        reason: CloseReason = CloseReason(CloseReason.Codes.NORMAL, "Disconnect confirm")
    ) {
        scope.launch {
            val num = index.toIntOrNull() ?: return@launch
            val client = model.getOrNull(num) ?: return@launch
            client.session.close(reason)
        }
    }
}
