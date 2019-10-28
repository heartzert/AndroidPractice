package heartzert.test.all

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.coordinatelayout.CoordinateLayoutAct
import heartzert.test.all.coordinatelayout.CoordinateTest2Activity
import heartzert.test.all.customview.ViewTestAct
import heartzert.test.all.nestedscrollview.NestedScrollViewAct
import heartzert.test.all.recyclertest.RecyclerViewTestActivity
import heartzert.test.all.recyclertest.recyclerviewWithFragment.RecyclerViewWithFragmentActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity
import heartzert.test.all.uitest.TextSizeTestActivity
import heartzert.test.all.uitest.UITestAct
import heartzert.test.all.viewpager_fragment.ViewpagerActivity
import java.io.File
import java.io.FileOutputStream
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.PendingIntent.getActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



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
        startActivity(Intent(this, ViewTestAct::class.java))
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
        val a  =  Array<String>(1, {WRITE_EXTERNAL_STORAGE})
        requestPermissions(this,a, 1)
        Log.d("========", "${externalCacheDir}")
        file.createNewFile()
        val stream = FileOutputStream(file)
        stream.write("I know that.".toByteArray())
        stream.close()
    }
}
