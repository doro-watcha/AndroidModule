package com.goddoro.module.presentation.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.module.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAnimationBinding.inflate(LayoutInflater.from(this))

        setContentView(mBinding.root)
    }
}