package com.goddoro.module.presentation.retry.workManager

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.goddoro.module.presentation.retry.room.ImageItem
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get


/**
 * created By DORO 5/17/21
 */

class UploadWorker(context: Context, params: WorkerParameters) : RxWorker(context, params),
    KoinComponent {

    private val TAG = UploadWorker::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    override fun createWork(): Single<Result> {

        val json = inputData.getString(ImageItem.DATA_UPLOAD_REQUEST_JSON)!!

        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        val imageItem = gson.fromJson(json, ImageItem::class.java)

        return Single.create { emitter ->

            Log.d(TAG, "Image Upload 작업 시작")

        }

    }


    override fun onStopped() {
        super.onStopped()
        compositeDisposable.clear()

    }


}