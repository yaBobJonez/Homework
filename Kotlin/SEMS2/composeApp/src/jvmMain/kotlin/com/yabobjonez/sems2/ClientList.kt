package com.yabobjonez.sems2

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ClientList(model: SnapshotStateList<Client>) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn (
        state = scrollState,
        modifier = Modifier.padding(16.dp)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            )
    ) {
        items(model.size) { i ->
            val client = model[i]
            //val isSelected = client.isSelected.value
            Row(
                modifier = Modifier
                    //.clickable { client.isSelected.value = !isSelected }
                    //.background(if (isSelected) Color.LightGray else Color.Transparent)
                    .padding(8.dp)
            ) {
                Text(text = "ID\t${i+1}", modifier = Modifier.weight(1f))
                Text(text = "IP\t${client.ip}", modifier = Modifier.weight(1f))
            }
        }
    }
}
