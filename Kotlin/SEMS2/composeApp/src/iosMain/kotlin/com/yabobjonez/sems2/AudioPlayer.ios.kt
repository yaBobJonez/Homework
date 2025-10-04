package com.yabobjonez.sems2

import kotlinx.cinterop.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSData
import platform.Foundation.create
import sems2.composeapp.generated.resources.Res

@OptIn(ExperimentalForeignApi::class)
actual class AudioPlayer {
    private var audioPlayer: AVAudioPlayer? = null
    private lateinit var files: Map<String, NSData>
    init {
        CoroutineScope(Dispatchers.Default).launch {
            files = mapOf(
                "fire" to Res.readBytes("files/fire.wav").toNSData(),
                "clear" to Res.readBytes("files/clear.wav").toNSData()
            )
        }
    }

    actual fun playAlarm() {
        stop()
        audioPlayer = AVAudioPlayer(files["fire"]!!, error = null)
        audioPlayer?.numberOfLoops = -1
        audioPlayer?.prepareToPlay()
        audioPlayer?.play()
    }

    actual fun playAllClear() {
        stop()
        audioPlayer = AVAudioPlayer(files["clear"]!!, error = null)
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

    @OptIn(BetaInteropApi::class)
    fun ByteArray.toNSData(): NSData = memScoped {
        NSData.create(bytes = allocArrayOf(this@toNSData), length = this@toNSData.size.convert())
    }
}