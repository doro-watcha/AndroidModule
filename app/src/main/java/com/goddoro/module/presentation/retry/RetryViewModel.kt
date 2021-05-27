package com.goddoro.module.presentation.retry

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.module.presentation.retry.room.ImageDao
import com.goddoro.module.presentation.retry.room.ImageItem
import com.goddoro.module.utils.Once
import com.goddoro.module.utils.addSchedulers
import com.goddoro.module.utils.disposedBy
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 5/17/21
 */

class RetryViewModel (
    val imageDao : ImageDao
) : ViewModel() {


    lateinit var imageItem : ImageItem
    private val TAG = RetryViewModel::class.java.simpleName
    private val compositeDisposable = CompositeDisposable()

    val images : MutableLiveData<List<ImageItem>> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listImages()
    }

    private fun listImages() {
        Log.d(TAG, "LIST IMAGES")
        imageDao.listImages()
            .addSchedulers()
            .subscribe({
                images.value = it
                onLoadCompleted.value = Once(Unit)
                Log.d(TAG, it.toString())
            }, {
                Log.d(TAG, it.message.toString())
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

    fun refresh() {
        listImages()
    }


    fun insertImage(imageUri: Uri) {

        imageItem = ImageItem.generateNewImage(imageUri)

        imageDao.insertImage(imageItem)
            .addSchedulers()
            .subscribe({
                refresh()
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)


    }

    fun updateProfileImage(imageItem: ImageItem) {
        imageItem.startUpload()
    }

}