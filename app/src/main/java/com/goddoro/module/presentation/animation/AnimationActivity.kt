package com.goddoro.module.presentation.animation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityAnimationBinding
import com.goddoro.module.utils.component.GridSpacingItemDecoration
import com.goddoro.module.utils.debugE
import com.goddoro.module.utils.disposedBy
import com.goddoro.module.utils.rxRepeatTimer
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimationActivity : AppCompatActivity() {

    private val TAG = AnimationActivity::class.java.simpleName


    private val compositeDisposable = CompositeDisposable()
    private lateinit var mBinding : ActivityAnimationBinding

    private val mViewModel : AnimationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAnimationBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)


        initView()
        initNotification()
        initButton()
        setupRecyclerView()
        initLottie()
        initTextView()
    }

    private fun initView() {


        var curProgress = 0
        rxRepeatTimer(100){
            debugE(TAG,it)
            curProgress += 1
            mBinding.progressRotate2.progress = curProgress
        }.disposedBy(compositeDisposable)


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

    private fun setupRecyclerView() {

        val imageLayoutManager = GridLayoutManager(this@AnimationActivity,3)
        val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
        val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

        val gridSpacing = GridSpacingItemDecoration(3,spacingLeft,spacingTop,0)

        mBinding.recyclerview.apply {

            layoutManager = imageLayoutManager
            addItemDecoration(gridSpacing)
            adapter = ImageAdapter().apply{

                clickItem.subscribe{
                    val intent = Intent ( this@AnimationActivity,AnimationDetailActivity::class.java)
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@AnimationActivity,it,"photo").toBundle())
                }.disposedBy(compositeDisposable)

            }
        }
    }

    private fun initLottie() {

        mBinding.lottie.apply {

            setOnClickListener {
                playAnimation()
            }
        }
    }

    private fun initTextView() {

        val text = arrayOf(
            "이동호", "이종민",
            "김혜연", "김태현", "장성원",
            "JunctionX", "GDM"
        )


        mBinding.txtFading.setTexts(text)
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {
        private const val CHANNEL_NOTIFICATION = "NOTIFICATION"
    }
}