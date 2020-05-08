package com.tarlochan.memoryflipgame

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer(var ctx: Context?) {
    var player: MediaPlayer? = null

    fun playSound(fileName: String?) {
        player = MediaPlayer()
        try {
            val afd = ctx!!.assets.openFd(fileName!!)
            player!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            player!!.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        player!!.start()
    }

}