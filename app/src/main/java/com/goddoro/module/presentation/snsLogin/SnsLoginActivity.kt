package com.goddoro.module.presentation.snsLogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.module.databinding.ActivitySnsLoginBinding

class SnsLoginActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivitySnsLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySnsLoginBinding.inflate(LayoutInflater.from(this))


        setContentView(mBinding.root)

        initGoogleLogin()
    }

    private fun initGoogleLogin() {



    }
}