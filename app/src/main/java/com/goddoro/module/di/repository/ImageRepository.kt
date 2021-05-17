package com.goddoro.module.di.repository

import android.net.Uri
import com.goddoro.module.model.ImageResponse


/**
 * created By DORO 5/17/21
 */

interface ImageRepository {

    suspend fun getMnistAnalysis(
        imageFile : Uri
    ) : ImageResponse
}