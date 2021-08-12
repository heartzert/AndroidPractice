package heartzert.test.all.samples.file

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Created by heartzert on 8/12/21.
 * Email: heartzert@163.com
 */
class FileTest {

    fun writeTest() {
        val file = File("/storage/emulated/0/XiaoYing/.public/.projects/test.txt")
        val writer = FileWriter(file)
        writer.write("123123")
        writer.close()
    }

    fun readTest(context: Context) {
        val file = File(context.externalCacheDir, "test")
        if (!file.exists()) return
        val reader = FileReader(file)
        Log.d("==========wxz", "${reader.readLines()}")
        reader.close()


    }
}