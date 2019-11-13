package heartzert.test.all

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import heartzert.test.all.android_conponents_paging.ui.ViewModelActivity
import heartzert.test.all.customview.ViewTestAct
import heartzert.test.all.nestedscrollview.custom.MyNestedScrollAct
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, ViewModelActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, MyNestedScrollAct::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, ViewTestAct::class.java))
    }

    fun writeFile() {
        val file = File("/storage/emulated/0/Android/data/com.huopin.dayfire/cache", "fff.txt")
        val a = Array<String>(1) { WRITE_EXTERNAL_STORAGE }
        requestPermissions(this, a, 1)
        Log.d("========", "${externalCacheDir}")
        file.createNewFile()
        val stream = FileOutputStream(file)
        stream.write("I know that.".toByteArray())
        stream.close()
    }
}
