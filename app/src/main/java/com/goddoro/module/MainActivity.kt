package com.goddoro.module

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.goddoro.module.databinding.ActivityMainBinding
import com.goddoro.module.presentation.animation.AnimationActivity
import com.goddoro.module.presentation.camera.CameraActivity
import com.goddoro.module.presentation.login.LoginActivity
import com.goddoro.module.presentation.mlkit.MlkitActivity
import com.goddoro.module.presentation.service.PlayerActivity
import com.goddoro.module.presentation.service.PlayerService
import com.goddoro.module.utils.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        initView()
        bindService()

    }

    private fun initView() {

        mBinding.apply {

            btnCamera.setOnClickListener {
                startActivity(CameraActivity::class)
            }
            btnLogin.setOnClickListener {
                startActivity(LoginActivity::class)
            }

            btnMlKit.setOnClickListener {
                startActivity(MlkitActivity::class)
            }

            btnAnimation.setOnClickListener {
                startActivity(AnimationActivity::class)
            }

            btnPlayer.setOnClickListener {
                startActivity(PlayerActivity::class)
            }
        }
    }

    private fun bindService() {





    }


}