package com.yabobjonez.sems2

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

enum class State {
    DISARMED,
    NORMAL,
    FIRE,
    ACKNOWLEDGED,
    SILENCED,
    DISCONNECTING,
    BLOCKING,
    UNBLOCKING,
}

class StateManager {
    val server = WSServer(this)
    val player = AudioPlayer()
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        server.start()
    }

    private var state by mutableStateOf(State.NORMAL)
    var alarmCause by mutableStateOf("")
    var displayInput by mutableStateOf("")

    val displayText by derivedStateOf {
        when (state) {
            State.DISARMED -> "SYSTEM DISARMED"
            State.NORMAL -> "SYSTEM ALL NORMAL"
            State.FIRE -> "FIRE ALARM (${alarmCause})"
            State.ACKNOWLEDGED -> "ALARM ACKNOWLEDGED (${alarmCause})"
            State.SILENCED -> "ALARM SILENCED (${alarmCause})"
            else -> displayInput
        }
    }
    val displayPlaceholder by derivedStateOf {
        when (state) {
            State.DISCONNECTING -> "Enter client ID to send close request"
            State.BLOCKING -> "Enter client IP address to block"
            State.UNBLOCKING -> "Enter client IP address to unblock"
            else -> ""
        }
    }

    val numpadEnabled by derivedStateOf {
        when (state) {
            State.DISCONNECTING,
            State.BLOCKING,
            State.UNBLOCKING -> true
            else -> false
        }
    }

    fun transition(newState: State) {
        val allowed = when (newState) {
            State.DISARMED -> state == State.NORMAL
            State.NORMAL -> {
                if (state in arrayOf(State.FIRE, State.ACKNOWLEDGED, State.SILENCED)) {
                    tryReset()
                    false
                } else true
            }
            State.FIRE -> state != State.DISARMED
            State.ACKNOWLEDGED -> state == State.FIRE
            State.SILENCED -> state in arrayOf(State.FIRE, State.ACKNOWLEDGED)
            State.DISCONNECTING,
            State.BLOCKING,
            State.UNBLOCKING -> state == State.NORMAL
        }
        if (!allowed) return
        state = newState
        when (state) {
            State.FIRE -> {
                player.playAlarm()
                scope.launch { server.broadcast("FIRE") }
            }
            State.NORMAL,
            State.ACKNOWLEDGED -> player.stop()
            State.SILENCED -> {
                player.stop()
                scope.launch { server.broadcast("mute") }
            }
            else -> {}
        }
    }

    private fun tryReset() {
        scope.launch {
            server.buttonStatusReplies.clear()
            server.broadcast("button_status?")
            delay(2000)
            if (server.buttonStatusReplies.size != server.model.size
                || server.buttonStatusReplies.any { it }) return@launch
            state = State.NORMAL
            withContext(Dispatchers.Main) {
                player.playAllClear()
            }
            server.broadcast("clear")
        }
    }

    fun performAction() {
        when (state) {
            State.DISCONNECTING -> {
                val index = displayInput.toIntOrNull() ?: return
                server.disconnect((index - 1).toString())
                state = State.NORMAL
            }
            State.BLOCKING -> {
                val reason = CloseReason(CloseReason.Codes.VIOLATED_POLICY, "IP address blocked")
                server.blockedIps.add(displayInput)
                server.model.withIndex().filter { it.value.ip == displayInput }.forEach {
                    val index = it.index.toString()
                    server.disconnect(index, reason)
                }
                state = State.NORMAL
            }
            State.UNBLOCKING -> {
                server.blockedIps.remove(displayInput)
                state = State.NORMAL
            }
            else -> {}
        }
        displayInput = ""
    }

    fun cancelAction() {
        displayInput = ""
        state = State.NORMAL
    }
}