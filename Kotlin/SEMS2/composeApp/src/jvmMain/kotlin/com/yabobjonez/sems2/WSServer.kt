package com.yabobjonez.sems2

import androidx.compose.runtime.snapshots.SnapshotStateList
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.staticResources
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

class WSServer(
    private var model: SnapshotStateList<Client>,
) {
    val blockedIps = mutableSetOf<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun start() {
        embeddedServer(Netty, port = 8080) {
            install(WebSockets)

            routing {
                staticResources("/alarmData", "files")
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
                                        broadcast("FIRE")
                                    }
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
        }.start(wait = false)
    }

    private suspend fun broadcast(message: String) {
        model.forEach { client ->
            try {
                client.session.send(Frame.Text(message))
            } catch (e: Exception) {
                println("Failed to send message '$message': ${e.message}")
            }
        }
    }

    fun trigger() {
        scope.launch {
            broadcast("FIRE")
        }
    }

    fun reset() {
        scope.launch {
            for (client in model) {
                try {
                    client.session.send(Frame.Text("button_status?"))
                    val frame = withTimeoutOrNull(2000) {
                        client.session.incoming.receive()
                    }
                    if (frame is Frame.Text) {
                        when (frame.readText()) {
                            "FIRE" -> {
                                trigger()
                                return@launch
                            }
                            "button_status = down" -> return@launch
                        }
                    }
                } catch (e: Exception) {
                    println("Error checking client status: ${e.message}")
                }
            }
            broadcast("clear")
        }
    }

    fun disconnect(
        index: String,
        reason: CloseReason = CloseReason(CloseReason.Codes.NORMAL, "Client disconnected")
    ) {
        scope.launch {
            val num = index.toIntOrNull() ?: return@launch
            val client = model.getOrNull(num) ?: return@launch
            client.session.close(reason)
        }
    }
}
