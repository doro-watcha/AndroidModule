package com.goddoro.module.presentation.animation

import android.animation.ValueAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.core.app.NotificationCompat
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityAnimationBinding
import com.goddoro.module.utils.debugE
import com.goddoro.module.utils.rxRepeatTimer

class AnimationActivity : AppCompatActivity() {

    private val TAG = AnimationActivity::class.java.simpleName


    private lateinit var mBinding : ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAnimationBinding.inflate(LayoutInflater.from(this))

        setContentView(mBinding.root)


        initView()
        initNotification()
        initButton()
    }

    private fun initView() {


        var curProgress = 0
        rxRepeatTimer(100){
            debugE(TAG,it)
            curProgress += 1
            mBinding.progressRotate2.progress = curProgress
        }


    }

    private fun initNotification () {

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm?.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_NOTIFICATION,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        nm?.notify(
            1,
            NotificationCompat
                .Builder(this, CHANNEL_NOTIFICATION)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle("AnimationDrawable")
                .setContentText("Animated icon will be shown!")
                .build()
        )

    }

    private fun initButton() {

        mBinding.button.setOnClickListener {
            it.isSelected = !it.isSelected
        }

        mBinding.button2.setOnClickListener {
            it.isSelected = false
            it.isSelected = true
        }
    }

    companion object {
        private const val CHANNEL_NOTIFICATION = "NOTIFICATION"
    }
}