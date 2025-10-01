package com.yabobjonez.sems2

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import sems2.composeapp.generated.resources.Res

actual class AudioPlayer(private val context: Context) {
    private var mediaPlayer = ExoPlayer.Builder(context).build()
    init {
        mediaPlayer.prepare()
    }

    actual fun playAlarm() {
        stop()
        val uri = Res.getUri("files/alarm.mp3")
        mediaPlayer.setMediaItem(MediaItem.fromUri(uri))
        mediaPlayer.repeatMode = Player.REPEAT_MODE_ONE
        mediaPlayer.play()
    }

    actual fun stop() {
        mediaPlayer.stop()
    }

    actual fun release() {
        mediaPlayer.release()
    }
}