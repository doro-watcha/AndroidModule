package com.goddoro.module.presentation.playerRecyclerView

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class Player ( val context : Context) {

    lateinit var player : SimpleExoPlayer

    fun init () {

        player = SimpleExoPlayer.Builder(context).build()
    }

    fun setupMediaItem( url : String ) {

        val mediaItem = MediaItem.fromUri(url)

        player.setMediaItem(mediaItem)
        player.prepare()


    }

    fun play () {

        player.play()
    }
}