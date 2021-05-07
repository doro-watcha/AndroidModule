package com.goddoro.module

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.goddoro.module.databinding.ActivityMainBinding
import com.goddoro.module.presentation.camera.CameraActivity
import com.goddoro.module.utils.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        initView()

    }

    private fun initView() {

        mBinding.apply {

            btnCamera.setOnClickListener {
                startActivity(CameraActivity::class)
            }
        }
    }


}