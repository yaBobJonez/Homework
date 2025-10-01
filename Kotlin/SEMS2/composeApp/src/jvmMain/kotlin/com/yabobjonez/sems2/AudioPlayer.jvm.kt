package com.yabobjonez.sems2

import sems2.composeapp.generated.resources.Res
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

actual class AudioPlayer {
    private var clip: Clip? = null

    actual fun playAlarm() {
        stop()
        val uri = Res.getUri("files/alarm.wav")
        val audioInputStream = AudioSystem.getAudioInputStream(File(uri))
        clip = AudioSystem.getClip()
        clip?.open(audioInputStream)
        clip?.loop(Clip.LOOP_CONTINUOUSLY)
        clip?.start()
    }

    actual fun stop() {
        clip?.stop()
    }

    actual fun release() {
        stop()
        clip?.close()
        clip = null
    }
}