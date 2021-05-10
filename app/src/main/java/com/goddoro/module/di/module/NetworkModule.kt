package com.goddoro.module.di.module

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * created By DORO 5/7/21
 */

private const val CONNECT_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

enum class ServerType(val apiUrl: String, val homepageUrl: String, val value: Int) {

    PRODUCTION("http://ec2-3-35-4-201.ap-northeast-2.compute.amazonaws.com:3000", "https://www.beatflo.co/", 0),
    DEVELOPMENT("https://api.staging.onesongtwoshows.com", "https://www.beatflo.co/", 1)
    ;

}


val networkModule = module{

    single {

        GsonBuilder().create()

    }

    single {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(get<Interceptor>())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ServerType.PRODUCTION.apiUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }

    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
            }.build())
        }
    }

}