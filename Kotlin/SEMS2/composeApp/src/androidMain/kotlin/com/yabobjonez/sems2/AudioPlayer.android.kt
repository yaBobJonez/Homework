package com.yabobjonez.sems2

import android.media.MediaDataSource
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sems2.composeapp.generated.resources.Res

actual class AudioPlayer {
    private var mediaPlayer = MediaPlayer()
    private lateinit var files: Map<String, MediaDataSource>
    init {
        CoroutineScope(Dispatchers.Default).launch {
            files = mapOf(
                "fire" to ByteArrayMediaDataSource(Res.readBytes("files/fire.wav")),
                "clear" to ByteArrayMediaDataSource(Res.readBytes("files/clear.wav"))
            )
        }
    }

    actual fun playAlarm() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(files["fire"])
        mediaPlayer.isLooping = true
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    actual fun playAllClear() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(files["clear"])
        mediaPlayer.isLooping = false
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    actual fun stop() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
    }

    actual fun release() {
        mediaPlayer.release()
    }
}

class ByteArrayMediaDataSource(private val data: ByteArray) : MediaDataSource() {
    override fun readAt(position: Long, buffer: ByteArray, offset: Int, size: Int): Int {
        if (position >= data.size) return -1
        val length = (data.size - position).coerceAtMost(size.toLong()).toInt()
        System.arraycopy(data, position.toInt(), buffer, offset, length)
        return length
    }
    override fun getSize(): Long = data.size.toLong()
    override fun close() {}
}
