package com.fftsun.android.lib

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.MediaPlayer

/**
 * 短时音效播放
 */
object SoundEffectUtil {

    private var mediaPlayer: MediaPlayer? = null

    /**
     * 播放音效
     */
    fun playEffect(manager: AssetManager?, fileName: String?) {
        if (manager == null || fileName.isNullOrEmpty()) return

        destroyPlayer()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.let { player ->
            try {
                val fd: AssetFileDescriptor = manager.openFd(fileName)
                player.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                player.prepare()
                player.start()
                player.setVolume(1f, 1f)
                player.setOnCompletionListener { destroyPlayer() }
            } catch (ex: Exception) {
                destroyPlayer()
            }
        }
    }

    private fun destroyPlayer() {
        mediaPlayer?.let { player ->
            try {
                player.stop()
                player.release()
            } catch (ex: Exception) {

            } finally {
                mediaPlayer = null
            }
        }
    }

}