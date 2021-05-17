package com.goddoro.module.presentation.retry

import android.net.Uri
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

    private val TAG = RetryViewModel::class.java.simpleName
    private val compositeDisposable = CompositeDisposable()

    val images : MutableLiveData<List<ImageItem>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listImages()
    }

    private fun listImages() {
        imageDao.listImages()
            .addSchedulers()
            .subscribe({
                images.value = it
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

    fun refresh() {
        listImages()
    }


}