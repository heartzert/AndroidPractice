package heartzert.test.all

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream

/**
 * Created by heartzert on 2020/5/12.
 * Email: heartzert@163.com
 */

fun testJson(view: View) {
    val t2 = Test2("p1", 2, "p3")
    val t = Test1("p1", 2, "p3", t2)
    val log = Gson().toJson(t)
    val tt = Gson().fromJson<Test1>(log, Test1::class.java)
    Log.d("========", "$log")
    Log.d("========", "$tt")
}

fun writeFile(activity: AppCompatActivity) {
    val file = File("/storage/emulated/0/Android/data/com.huopin.dayfire/cache", "fff.txt")
    val a = Array<String>(1) { permission.WRITE_EXTERNAL_STORAGE }
    ActivityCompat.requestPermissions(activity, a, 1)
    Log.d("========", "${activity.applicationContext.externalCacheDir}")
    file.createNewFile()
    val stream = FileOutputStream(file)
    stream.write("I know that.".toByteArray())
    stream.close()
}

data class Test1(
    val t1: String,
    val t2: Int,
    val t3: String,
    val t4: Test2
)

data class Test2(
    val tt1: String,
    val tt2: Int,
    val tt3: String
)

//测试打开图片的隐士intent
private fun testIntent(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW) //打开图片得启动ACTION_VIEW意图

    val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.iamge_square_yellow)

    val uri: Uri = Uri.parse(
        MediaStore.Images.Media.insertImage(
            context.contentResolver, bitmap, null,
            null
        )
    ) //将bitmap转换为uri

    intent.setDataAndType(uri, "image/*") //设置intent数据和图片格式

    context.startActivity(intent)
}