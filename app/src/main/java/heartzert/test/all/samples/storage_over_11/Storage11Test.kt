package heartzert.test.all.samples.storage_over_11

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.InputStream
import java.io.OutputStream


/**
 * Created by heartzert on 8/13/21.
 * Email: heartzert@163.com
 */
object Storage11Test {

    fun registerStartSAF(activity: FragmentActivity): ActivityResultLauncher<Intent> {
        //registerForActivityResult必须在oncreate之内调用，相应的launch在点击时调用
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val uri = result.data?.data ?: return@registerForActivityResult
            activity.contentResolver.openInputStream(uri).use {
                it ?:return@registerForActivityResult
                Log.d("==========wxz", "read content:${String(it.readBytes())}")
            }
        }
    }

    fun startSAF(luncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        //指定选择文本类型的文件
        intent.type = "text/plain"

        luncher.launch(intent)
    }

    private fun insert2Album(context: Context, inputStream: InputStream?, fileName: String) {
        if (inputStream == null) return
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //RELATIVE_PATH 字段表示相对路径-------->(1)
            contentValues.put(
                MediaStore.Images.ImageColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            )
        } else {
            val dstPath =
                (Environment.getExternalStorageDirectory().path + File.separator + Environment.DIRECTORY_PICTURES
                        + File.separator + fileName)
            //DATA字段在Android 10.0 之后已经废弃
            contentValues.put(MediaStore.Images.ImageColumns.DATA, dstPath)
        }

        //插入相册------->(2)
        val uri =
            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

        //写入文件------->(3)
        write2File(context, uri, inputStream)
    }

    //uri 关联着待写入的文件
    //inputStream 表示原始的文件流
    private fun write2File(context: Context, uri: Uri?, inputStream: InputStream?) {
        if (uri == null || inputStream == null) return
        try {
            //从Uri构造输出流
            val outputStream = context.contentResolver.openOutputStream(uri) ?: return
            val `in` = ByteArray(1024)
            var len = 0
            do {
                //从输入流里读取数据
                len = inputStream.read(`in`)
                if (len != -1) {
                    outputStream.write(`in`, 0, len)
                    outputStream.flush()
                }
            } while (len != -1)
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            Log.d("test", e.localizedMessage)
        }
    }
}