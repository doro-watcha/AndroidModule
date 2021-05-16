package com.goddoro.module.presentation.mlkit

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.net.toUri
import com.goddoro.module.utils.Broadcast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions


/**
 * created By DORO 5/10/21
 */


class DoroAnalyzer : ImageAnalysis.Analyzer {

    private val TAG = DoroAnalyzer::class.java.simpleName

    val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

    val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
        .setLanguageHints(listOf("ko","en", "hi"))
        .build()

    init {


    }


    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }


    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {

        //Log.d(TAG, "WHY?")
        val mediaImage = image.image

        if ( mediaImage != null) {

            val image = FirebaseVisionImage.fromMediaImage(mediaImage,FirebaseVisionImageMetadata.ROTATION_0)

            val result = detector
                .processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    // Task completed successfully
                    // ...
                    if ( firebaseVisionText.text.contains("DONG")){
                        Broadcast.cardFindBroadcast.onNext(Unit)
                    }
                    Log.d(TAG,firebaseVisionText.text)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                    //Log.d(TAG,e.message.toString())
                }

        }

        Thread.sleep(100)

        image.close()
    }


}