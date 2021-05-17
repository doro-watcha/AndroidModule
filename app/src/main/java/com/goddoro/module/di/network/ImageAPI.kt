package com.goddoro.module.di.network

import com.goddoro.module.model.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * created By DORO 5/17/21
 */

interface ImageAPI {



    @POST("/mnist")
    @Multipart
    suspend fun getMnistAnalysis (
        @Part imageFile: MultipartBody.Part
    ) : ImageResponse
}