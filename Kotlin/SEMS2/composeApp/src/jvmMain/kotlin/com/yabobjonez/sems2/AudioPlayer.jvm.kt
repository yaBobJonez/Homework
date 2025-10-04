package com.yabobjonez.sems2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sems2.composeapp.generated.resources.Res
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

actual class AudioPlayer {
    private val clips = mutableMapOf<String, Clip>()
    init {
        CoroutineScope(Dispatchers.Default).launch {
            for (name in arrayOf("fire", "clear")) {
                val bytes = Res.readBytes("files/$name.wav")
                val stream = BufferedInputStream(ByteArrayInputStream(bytes))
                val clip = AudioSystem.getClip()
                clip.open(AudioSystem.getAudioInputStream(stream))
                clips[name] = clip
            }
        }
    }

    actual fun playAlarm() {
        stop()
        val clip = clips["fire"]!!
        clip.loop(Clip.LOOP_CONTINUOUSLY)
        clip.start()
    }

    actual fun playAllClear() {
        stop()
        val clip = clips["clear"]!!
        clip.start()
    }

    actual fun stop() {
        for (clip in clips.values) {
            if (clip.isRunning) clip.stop()
            clip.microsecondPosition = 0L
        }
    }

    actual fun release() {
        for (clip in clips.values) {
            if (clip.isRunning) clip.stop()
            clip.close()
        }
        clips.clear()
    }
}