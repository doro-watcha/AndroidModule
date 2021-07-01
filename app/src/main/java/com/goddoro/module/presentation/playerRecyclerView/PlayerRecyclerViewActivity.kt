package com.goddoro.module.presentation.playerRecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.module.databinding.ActivityPlayerRecyclerViewBinding
import org.koin.android.ext.android.inject

class PlayerRecyclerViewActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityPlayerRecyclerViewBinding

    private val mViewModel : PlayerRecyclerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding= ActivityPlayerRecyclerViewBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)


        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        mBinding.apply {

            mRecyclerView.adapter = RecyclerViewAdapter(this@PlayerRecyclerViewActivity)
        }



    }
}