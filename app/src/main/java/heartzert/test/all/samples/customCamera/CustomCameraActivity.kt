package heartzert.test.all.samples.customCamera

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import heartzert.test.all.R
import kotlinx.coroutines.launch

/**
 * Created by heartzert on 2024/05/13.
 * Email: heartzert@163.com
 */
class CustomCameraActivity: AppCompatActivity() {

    lateinit var previewView: PreviewView
    lateinit var photo: View

    val cameraXHelper by lazy { CameraXHelper(this, previewView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_camera)

        previewView = findViewById(R.id.preview_view)
        photo = findViewById(R.id.image_capture_button)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 已经有相机权限
            cameraXHelper.initCamera()
        } else {
            // 没有相机权限，请求相机权限
            cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
        }

        photo.setOnClickListener {
            lifecycleScope.launch {
                val uri = cameraXHelper.takePhoto()

            }
        }

    }

    private val cameraPermissionRequest =registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            cameraXHelper.initCamera()
        } else {
            Toast.makeText(this, "你有病吧", Toast.LENGTH_SHORT).show()
        }
    }
}