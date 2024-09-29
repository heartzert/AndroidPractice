package heartzert.test.all.samples.customCamera

import android.annotation.SuppressLint
import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.MeteringPoint
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume

/**
 * Created by heartzert on 2024/05/13.
 * Email: heartzert@163.com
 */
class CameraXHelper(
    val activity: AppCompatActivity,
    val previewView: PreviewView
) {

    private val TAG = "CameraXHelper"

    private var imageCapture: ImageCapture? = null

    private var camera: Camera? = null

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(activity, cameraSelector, preview, imageCapture)

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
                return@addListener
            }

            setGestureListener()

        }, ContextCompat.getMainExecutor(activity))
    }

    suspend fun takePhoto(): Uri? {
        return suspendCancellableCoroutine {
            // Get a stable reference of the modifiable image capture use case
            val imageCapture = imageCapture ?: run {
                it.resume(null)
                return@suspendCancellableCoroutine
            }

            // Create time stamped name and MediaStore entry.
            val name = "Camera_Test_" + SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US).format(System.currentTimeMillis())
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
                }
            }

            // Create output options object which contains file + metadata
            val outputOptions = ImageCapture.OutputFileOptions
                .Builder(activity.contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build()

            // Set up image capture listener, which is triggered after photo has
            // been taken
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(activity),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        it.resume(null)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val msg = "Photo capture succeeded: ${output.savedUri}"
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, msg)
                        it.resume(output.savedUri)
                    }
                }
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setGestureListener() {
        val gestureDetector = GestureDetectorCompat(activity, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean = true

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val meteringPointFactory = previewView.meteringPointFactory
                val focusPoint = meteringPointFactory.createPoint(e.x, e.y)
                focus(focusPoint)
                return true
            }
        })

        val scaleGestureDetector = ScaleGestureDetector(
            activity,
            object : SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    scale(detector.scaleFactor)
                    return true
                }
            })

        previewView.setOnTouchListener { _, event ->
            var didConsume = scaleGestureDetector.onTouchEvent(event)
            if (!scaleGestureDetector.isInProgress) {
                didConsume = gestureDetector.onTouchEvent(event)
            }
            didConsume
        }
    }

    fun focus(meteringPoint: MeteringPoint) {
        val camera = camera ?: return

        val meteringAction = FocusMeteringAction.Builder(meteringPoint).build()
        camera.cameraControl.startFocusAndMetering(meteringAction)
    }

    fun scale(scaleFactor: Float) {
        val camera = camera ?: return
        val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1f
        camera.cameraControl.setZoomRatio(scaleFactor * currentZoomRatio)
    }
}