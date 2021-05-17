package com.goddoro.module.presentation.animation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * created By DORO 5/18/21
 */

class AnimationViewModel : ViewModel() {

    val images : MutableLiveData<List<Unit>> = MutableLiveData()

    init {

        images.value = listOf(Unit,Unit,Unit,Unit,Unit,Unit,Unit)
    }
}