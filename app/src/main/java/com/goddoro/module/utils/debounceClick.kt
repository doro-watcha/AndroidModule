package com.goddoro.module.utils

import android.view.View
import androidx.databinding.BindingAdapter


/**
 * created By DORO 5/10/21
 */


typealias OnClickListener = (View) -> Unit

class OnDebounceClickListener(private val listener: OnClickListener) : View.OnClickListener {
    override fun onClick(v: View?) {
        val now = System.currentTimeMillis()
        if (now < lastTime + INTERVAL) return
        lastTime = now
        v?.run(listener)
    }

    companion object {
        private const val INTERVAL: Long = 800L
        private var lastTime: Long = 0L
    }
}


infix fun View.setOnDebounceClickListener(listener: OnClickListener) {
    this.setOnClickListener(OnDebounceClickListener {
        it.run(listener)
    })
}

@BindingAdapter("android:onDebounceClick")
fun View.setOnDebounceClickListener(listener: View.OnClickListener?) {
    if (listener == null) {
        this.setOnClickListener(null)
    } else {
        this.setOnClickListener(OnDebounceClickListener {
            listener.onClick(it)
        })
    }
}
