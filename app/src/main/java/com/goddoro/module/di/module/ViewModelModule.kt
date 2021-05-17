package com.goddoro.module.di.module

import com.goddoro.module.presentation.login.LoginViewModel
import com.goddoro.module.presentation.retry.RetryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * created By DORO 5/7/21
 */

val viewModelModule = module {


    viewModel { LoginViewModel(get()) }
    viewModel { RetryViewModel() }
}