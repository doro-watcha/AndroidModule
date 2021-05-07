package com.goddoro.module.presentation.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.module.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCameraBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
    }
}