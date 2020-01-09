package heartzert.test.all

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.gson.Gson
import heartzert.test.all.constraintlayout.ConstraintLayoutChangeAct
import heartzert.test.all.motionLayout.MotionLayoutActivity
import heartzert.test.all.recyclertest.myRecyclerView.NestedTestActivity
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, NestedTestActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, ConstraintLayoutChangeAct::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, MotionLayoutActivity::class.java))
    }

    fun testJson(view: View) {
        val t2 = Test2("p1", 2, "p3")
        val t = Test1("p1", 2, "p3", t2)
        val log = Gson().toJson(t)
        val tt = Gson().fromJson<Test1>(log, Test1::class.java)
        Log.d("========", "$log")
        Log.d("========", "$tt")
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
}
