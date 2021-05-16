package com.goddoro.module.di.module
//
//import androidx.room.Room
//import com.goddoro.module.presentation.retry.room.ImageDatabase
//import org.koin.dsl.bind
//import org.koin.dsl.module
//
//
///**
// * created By DORO 5/10/21
// */
//
//val roomModule = module {
//    single{
//        Room.databaseBuilder(get(), ImageDatabase::class.java, "i01")
//            .allowMainThreadQueries()
//            .build()
//    } bind ImageDatabase::class
//
//    single {
//        get<ImageDatabase>().imageDao()
//    }
//}