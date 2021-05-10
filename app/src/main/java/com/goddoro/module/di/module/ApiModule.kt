package com.goddoro.module.di.module

import com.goddoro.module.di.network.LoginAPI
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * created By DORO 5/10/21
 */


val apiModule = module {

    single { get<Retrofit>().create(LoginAPI::class.java) }
}