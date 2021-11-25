package heartzert.lib.utils

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by heartzert on 9/2/21.
 * Email: heartzert@163.com
 */
object StorageUtil {

  @JvmStatic
  @JvmOverloads
  fun getExternalStorage(context: Context, string: String? = null): File {
    val state = Environment.getExternalStorageState()
    return if (Environment.MEDIA_MOUNTED == state) {
      context.getExternalFilesDir(string) ?: kotlin.run {
        val file: File? = Environment.getExternalStorageDirectory()
        val filepath = file?.path
        filepath ?: context.filesDir
        val realFilePath = if (filepath!!.endsWith(File.separator)) {
          filepath.removeSuffix(File.separator)
        } else {
          filepath
        }
        if (realFilePath.endsWith(context.packageName)) {
          File(file, string ?: "")
        } else {
          File(context.filesDir, string ?: "")
        }
      }
    } else {
      File(context.filesDir, string ?: "")
    }
  }
}