package com.goddoro.module.di.module

import com.goddoro.module.di.network.LoginAPI
import com.goddoro.module.di.repository.LoginRepository
import com.goddoro.module.di.repositoryImpl.LoginRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * created By DORO 5/10/21
 */


val repositoryModule = module {


    single { LoginRepositoryImpl(get()) } bind LoginRepository::class

}