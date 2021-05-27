package com.goddoro.module.di.module

import com.goddoro.module.utils.MultiPartUtil
import com.goddoro.module.utils.ScreenUtil
import com.goddoro.module.utils.UriUtil
import org.koin.dsl.module


/**
 * created By DORO 5/17/21
 */


val utilModule = module{


    single { UriUtil(get()) }
    single { MultiPartUtil(get()) }
    single { ScreenUtil(get()) }
}