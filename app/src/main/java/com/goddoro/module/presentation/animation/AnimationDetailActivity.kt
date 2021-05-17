package com.goddoro.module.presentation.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import android.transition.*
import com.goddoro.module.databinding.ActivityAnimationDetailBinding

class AnimationDetailActivity : AppCompatActivity() {

    private val TAG = AnimationDetailActivity::class.java.simpleName

    private lateinit var mBinding : ActivityAnimationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAnimationDetailBinding.inflate(LayoutInflater.from(this))


        setContentView(mBinding.root)

        window.sharedElementEnterTransition = TransitionSet().apply {
            interpolator = OvershootInterpolator(0.7f)
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(ChangeBounds().apply{
                pathMotion = ArcMotion()
            })
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
        }
    }
}