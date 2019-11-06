package heartzert.test.all

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import heartzert.test.all.nestedscrollview.custom.MyNestedScrollAct
import heartzert.test.all.recyclertest.recyclerviewWithFragment.RecyclerViewWithFragmentActivity
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun nestedScrollView(view: View) {
//        startActivity(Intent(this, NestedScrollViewAct::class.java))
//        startActivity(Intent(this, CoordinateTest2Activity::class.java))
//        startActivity(Intent(this, RecyclerViewTestActivity::class.java))
        startActivity(Intent(this, RecyclerViewWithFragmentActivity::class.java))
    }

    fun coordinateLayout(view: View) {
        startActivity(Intent(this, MyNestedScrollAct::class.java))
    }

    fun test(view: View) {
//        startActivity(Intent(this, ConstraintLayoutChangeAct::class.java))
//        startActivity(Intent(this, UITestAct::class.java))
//        startActivity(Intent(this, TextSizeTestActivity::class.java))
//        startActivity(Intent(this, ViewpagerActivity::class.java))
        startActivity(Intent(this, ShadowActActivity::class.java))
//        startActivity(Intent(this, TestLSCVAct::class.java))
//        startActivity(Intent(this, UITestAct::class.java))
//        startActivity(Intent(this, AsyncTaskTestAct::class.java))
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
