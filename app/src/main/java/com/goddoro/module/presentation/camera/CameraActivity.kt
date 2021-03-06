package com.goddoro.module.presentation.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.core.impl.VideoCaptureConfig
import androidx.camera.core.impl.utils.executor.CameraXExecutors
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.goddoro.module.CommonConst.ARG_IMG_URI
import com.goddoro.module.R
import com.goddoro.module.databinding.ActivityCameraBinding
import com.goddoro.module.presentation.mlkit.DoroAnalyzer
import com.goddoro.module.presentation.retry.RetryActivity
import com.goddoro.module.utils.Broadcast
import com.goddoro.module.utils.disposedBy
import com.goddoro.module.utils.startActivity
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.roundToInt

typealias LumaListener = (luma: Double) -> Unit

class CameraActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private var currentCameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var imageCapture: ImageCapture
    private lateinit var videoRecorder : VideoCapture
    private lateinit var videoRecordOptions : VideoCapture.OutputFileOptions

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var mBinding : ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCameraBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera(currentCameraSelector)
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listener for take photo button
        outputDirectory = getOutputDirectory()

        mBinding.cameraCaptureButton.setOnClickListener { takePhoto() }
        mBinding.cameraRecordButton.setOnClickListener{ startRecord() }
        mBinding.cameraStopButton.setOnClickListener { stopRecord() }
        mBinding.btnSwitch.setOnClickListener { switchCamera() }

        cameraExecutor = Executors.newSingleThreadExecutor()

        setupBroadcast()
    }

    private fun takePhoto() {
        Log.d(TAG,imageCapture.toString())
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        Log.d(TAG,outputDirectory.toString())
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")
        Log.d(TAG,photoFile.toString())

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"

                    val intent = Intent ( this@CameraActivity , RetryActivity::class.java)

                    intent.putExtra(ARG_IMG_URI,savedUri)


                    startActivity(intent)

                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })

    }
    @SuppressLint("RestrictedApi")
    private fun startRecord () {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        videoRecorder.startRecording(
            videoRecordOptions,
            CameraXExecutors.mainThreadExecutor(),
            object : VideoCapture.OnVideoSavedCallback {

                override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                    Toast.makeText(this@CameraActivity,"SAVE COMPLETED",Toast.LENGTH_SHORT).show()
                }

                override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                    Log.d(TAG,videoCaptureError.toString())
                    Log.d(TAG,cause.toString())
                    Toast.makeText(this@CameraActivity,message,Toast.LENGTH_SHORT).show()
                }
            })

    }
    @SuppressLint("RestrictedApi")
    private fun stopRecord() {
        videoRecorder.stopRecording()
    }

    private fun switchCamera () {
        currentCameraSelector = if ( currentCameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA){
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        startCamera(currentCameraSelector)


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera(currentCameraSelector)
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera( cameraSelector: CameraSelector ) {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(mBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            videoRecorder = VideoCapture.Builder()
                .build()

            videoRecordOptions = VideoCapture.OutputFileOptions.Builder(File(outputDirectory.absolutePath + File.separator + "output2.mp4")).build()


            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, DoroAnalyzer() )
                }


            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            cardFindBroadcast.subscribe{
                mBinding.txtFind.visibility= View.VISIBLE
            }.disposedBy(compositeDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        cameraExecutor.shutdown()
    }

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }
    companion object {
        private val TAG = CameraActivity::class.java.simpleName
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO)
    }
}