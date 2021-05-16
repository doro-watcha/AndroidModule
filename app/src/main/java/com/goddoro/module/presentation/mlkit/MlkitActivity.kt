package com.goddoro.module.presentation.mlkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.net.toUri
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityMlkitBinding
import com.goddoro.module.presentation.camera.CameraActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MlkitActivity : AppCompatActivity() {

    private val TAG = MlkitActivity::class.java.simpleName

    val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
//
//    val detector = FirebaseVision.getInstance().cloudTextRecognizer
//// Or, to change the default settings:
//// val detector = FirebaseVision.getInstance().getCloudTextRecognizer(options)
//
//    // Or, to provide language hints to assist with language detection:
//// See https://cloud.google.com/vision/docs/languages for supported languages
    val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
        .setLanguageHints(listOf("en", "hi"))
        .build()

    private lateinit var mBinding : ActivityMlkitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMlkitBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        setupAnalyzer()
    }

    private fun setupAnalyzer() {

        val imageFile = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name))
        }!!

        val photoFile = File(
            imageFile.absolutePath,
            "good" + ".jpg")

        val result = detector.processImage(FirebaseVisionImage.fromFilePath(this,photoFile.toUri()))
            .addOnSuccessListener { firebaseVisionText ->
                // Task completed successfully
                // ...
                Log.d(TAG,firebaseVisionText.text)
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.d(TAG,e.message.toString())
            }

    }
}