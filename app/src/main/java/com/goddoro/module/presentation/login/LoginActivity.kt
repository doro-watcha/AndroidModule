package com.goddoro.module.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.goddoro.module.databinding.ActivityLoginBinding
import com.goddoro.module.utils.observeOnce
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityLoginBinding
    private val mViewModel : LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        observeViewModel()
    }

    private fun observeViewModel() {


        mViewModel.apply {

            loginSuccess.observeOnce(this@LoginActivity){
                Toast.makeText(this@LoginActivity,"${it.name}님 로그인에 성공하셨습니다!",Toast.LENGTH_SHORT).show()
            }
            errorInvoked.observeOnce(this@LoginActivity){
                Toast.makeText(this@LoginActivity,"로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }

        }

    }
}