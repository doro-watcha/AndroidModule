package com.goddoro.module.presentation.retry

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.module.CommonConst.ARG_IMG_URI
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityRetryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetryActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityRetryBinding

    private val mViewModel : RetryViewModel by viewModel()

    private lateinit var imageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRetryBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)


        initView()
        observeViewModel()
    }

    private fun initView() {
        imageUri = intent.getParcelableExtra(ARG_IMG_URI)!!
        mBinding.imgCaptured.setImageURI(imageUri)

    }

    private fun observeViewModel() {

        mViewModel.apply {



        }
    }
}