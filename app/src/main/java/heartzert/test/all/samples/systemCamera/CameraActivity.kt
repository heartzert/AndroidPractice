package heartzert.test.all.samples.systemCamera

import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

//调用系统相机并通知系统相册刷新
class CameraActivity : AppCompatActivity() {

    //不同系统原生相机包名列表
    private val cameraPackageList = arrayOf(
        //小米、iQOO、红米、vivo
        "com.android.camera",
        //三星
        "com.sec.android.app.camera",
        //华为、honor
        "com.huawei.camera",
        //lava
        "com.mediatek.camera",
        //pixel、nexus
        "com.google.android.GoogleCamera",
        //美图
        "com.mlab.cam",
        //oppo
        "com.oppo.camera",
        //华硕
        "com.asus.camera",
        //中兴
        "zte.com.cn.camera",
        //motorola
        "com.motorola.camera",
        //诺基亚
        "com.evenwell.camera2",
        //sony
        "com.sonyericsson.android.camera",
        //联想
        "com.sprdroid.resource.camera",
        //acer
        "com.acer.camera",
        //LG
        "com.lge.camera",
        //一加
        "com.oneplus.camera",
        //魅族
        "com.meizu.media.camera",
        //htc
        "com.htc.camera",
        //锤子
        "com.android.camera2"
    )

    companion object {
        const val REQUEST_CODE_VIDEO = 1
        const val REQUEST_CODE_PICTURE = 2
    }

    private val cameraPath = (Environment.getExternalStorageDirectory()
        .toString() + File.separator
            + Environment.DIRECTORY_DCIM
            + File.separator
            + "Camera"
            + File.separator)

    lateinit var binding: ActivityCameraBinding

    private var filePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_VIDEO) {

            } else if (requestCode == REQUEST_CODE_PICTURE) {
                MediaScannerConnection.scanFile(
                    this, arrayOf(filePath), arrayOf("image/jpg")
                ) { path: String?, uri: Uri? ->
                    Log.d("==========wxz", "onActivityResult: complete")
                }
            }

        }
    }

    //https://blog.csdn.net/weixin_42105630/article/details/86305354
    fun photo(view: View) {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val format = SimpleDateFormat("yyyyMMdd_HHmmss")
        val date = Date(System.currentTimeMillis())
        val fileName = format.format(date)
        filePath =
            cameraPath + "IMG_" + fileName + ".jpg"
        val file: File = File(filePath)
        val photoUri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            file
        )
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        if (takePhotoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_CODE_PICTURE)
        }
    }

    fun video(view: View) {
        val takePhotoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takePhotoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_CODE_VIDEO)
        }
    }


    //用指定包名打开相机
    private fun getPickIntent(actionVideoCapture: String): Intent? {
        val result = Intent(actionVideoCapture)
        packageManager ?: return null
        for (packageName in cameraPackageList) {
            result.setPackage(packageName)
            if (result.resolveActivity(packageManager) != null) {
                return result
            }
        }
        return null
    }

}