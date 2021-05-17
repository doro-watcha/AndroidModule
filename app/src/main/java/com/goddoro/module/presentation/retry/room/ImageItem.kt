package com.goddoro.module.presentation.retry.room

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.work.*
import com.goddoro.module.presentation.retry.workManager.UploadWorker
import com.goddoro.module.utils.addSchedulers
import com.goddoro.module.utils.disposedBy
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.parcel.Parcelize
import org.koin.core.KoinComponent
import org.koin.core.get


/**
 * created By DORO 5/17/21
 */

@Entity(tableName = "ImageItem")
@Parcelize
data class ImageItem(

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0,

    @Expose
    var updateTimeMs: Long = System.currentTimeMillis(),

    /**
     * Unique Id for this upload process,
     */
    @Expose
    val uniqueTimeStampId: String = System.currentTimeMillis().toString(),

    @Expose
    var imagePath: String


) : Parcelable, KoinComponent {





    /** [WorkManager]에 json 형태로 [UploadRequest]를 전달하기 위해 만들어 둔 gson 객체*/
    @Ignore
    private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()


    fun uploadImage(){


    }

    private fun toJson(): String {
        return gson.toJson(this)
    }


    fun startUpload() {

        /**
         * [Application] [Context]를 이용해서 [WorkManager] 객체 얻어옴
         */
        val appContext = get<Context>()
        val workManager = WorkManager.getInstance(appContext)

        /** 현재 객체를 JSON으로 만들어서 WorkManager의 input data로 준비함 */
        val json = toJson()
        val inputData = workDataOf(
            DATA_IMAGE_UPLOAD_JSON to json
        )

        /** 전면카메라 좌우반전 */

        val flipVideoRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(Constraints.NONE)
                .setInputData(inputData)
                .build()


        workManager.beginUniqueWork(
            id.toString(),
            ExistingWorkPolicy.REPLACE,
            flipVideoRequest
        )
            .enqueue()



    }

    private fun insertDatabase() : Disposable {
        val imageDao = get<ImageDao>()


        return imageDao.insertImage(this@ImageItem)
            .addSchedulers()
            .subscribe({
                Log.d(TAG,"Image Insert Success")
                this@ImageItem.id = it.toInt()

            },{
                Log.d(TAG, "Image Insert Fail")
            })


    }

    private fun updateDatabase() {



    }

    companion object {


        const val DATA_IMAGE_UPLOAD_JSON = "DATA_IMAGE_UPLOAD_JSON"
        private val TAG = ImageItem::class.java.simpleName

        fun generateNewImage ( uri : Uri) : ImageItem {

            val image = ImageItem(
                imagePath = uri.toString()
            )

            image.insertDatabase()

            return image
        }
    }



}