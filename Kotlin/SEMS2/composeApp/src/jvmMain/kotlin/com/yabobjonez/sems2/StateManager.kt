package com.yabobjonez.sems2

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.ktor.websocket.CloseReason

enum class State {
    NORMAL,
    DISCONNECTING,
    BLOCKING,
    UNBLOCKING,
}

class StateManager(
    private val server: WSServer,
    private val model: SnapshotStateList<Client>
) {
    var state by mutableStateOf(State.NORMAL)

    var displayInput by mutableStateOf("")

    val displayText by derivedStateOf {
        when (state) {
            State.NORMAL -> "SYSTEM ALL NORMAL"
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
            State.NORMAL -> true
            State.DISCONNECTING,
            State.BLOCKING,
            State.UNBLOCKING -> state == State.NORMAL
        }
        if (allowed) state = newState
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
                model.withIndex().filter { it.value.ip == displayInput }.forEach {
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