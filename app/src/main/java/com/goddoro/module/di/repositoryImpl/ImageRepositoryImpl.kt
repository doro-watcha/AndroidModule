package com.goddoro.module.di.repositoryImpl

import android.net.Uri
import com.goddoro.module.di.network.ImageAPI
import com.goddoro.module.di.repository.ImageRepository
import com.goddoro.module.model.ImageResponse
import com.goddoro.module.utils.MultiPartUtil


/**
 * created By DORO 5/17/21
 */

class ImageRepositoryImpl(
    private val api: ImageAPI,
    private val multiPartUtil : MultiPartUtil
) : ImageRepository {
    override suspend fun getMnistAnalysis(imageFile: Uri): ImageResponse {
        return api.getMnistAnalysis(multiPartUtil.uriToPart("image", imageFile))
    }


}