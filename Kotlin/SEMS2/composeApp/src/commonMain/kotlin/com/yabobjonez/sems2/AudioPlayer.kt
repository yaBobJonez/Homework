package com.yabobjonez.sems2

import sems2.composeapp.generated.resources.Res

expect class AudioPlayer() {
    fun playAlarm()
    fun playAllClear()
    fun stop()
    fun release()
}
