package com.yabobjonez.sems2

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import sems2.composeapp.generated.resources.Res

actual class AudioPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun playAlarm() {
        stop()
        val uri = Res.getUri("files/alarm.mp3")
        audioPlayer = AVAudioPlayer(contentsOfURL = NSURL.URLWithString(uri)!!, error = null)
        audioPlayer?.numberOfLoops = -1
        audioPlayer?.prepareToPlay()
        audioPlayer?.play()
    }

    actual fun stop() {
        audioPlayer?.stop()
    }

    actual fun release() {
        stop()
        audioPlayer = null
    }
}