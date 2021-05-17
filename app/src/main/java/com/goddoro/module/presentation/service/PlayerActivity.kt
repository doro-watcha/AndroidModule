package com.goddoro.module.presentation.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import com.goddoro.module.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private val TAG = PlayerActivity::class.java.simpleName

    private lateinit var mBinding : ActivityPlayerBinding


    private val playerServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBinder = service as PlayerService.PlayerBinder
            Log.d(TAG, "onServiceConnected")
            serviceBinder?.service?.showToast("onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBinder = null
        }
    }

    private var serviceBinder : PlayerService.PlayerBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityPlayerBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this


        setContentView(mBinding.root)

        bindService()
    }

    private fun bindService() {
        Intent(this,PlayerService::class.java).run {
            bindService(this, playerServiceConnection, Service.BIND_AUTO_CREATE)
        }
    }
}