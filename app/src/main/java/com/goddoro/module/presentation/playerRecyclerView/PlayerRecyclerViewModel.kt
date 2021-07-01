package com.goddoro.module.presentation.playerRecyclerView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerRecyclerViewModel : ViewModel(){

    val videoItems : MutableLiveData<List<VideoItem>> = MutableLiveData()

    init {

        videoItems.value = listOf(
            VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),
            VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),
            VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),
            VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0),VideoItem(0)
        )
    }
}