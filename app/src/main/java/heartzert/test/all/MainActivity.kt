package heartzert.test.all

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import heartzert.test.all.uitest.CommonUITestActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity
import heartzert.test.all.uitest.UITestAct
import heartzert.test.all.uitest.bubbles.BubblesTestActivity

class MainActivity : AppCompatActivity() {

    val mViewModel: MainViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(mViewModel)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
            }
        }, IntentFilter("$packageName.alarm"))

    }

    fun firstTest(view: View) {
        startActivity(Intent(this, BubblesTestActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, CommonUITestActivity::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, ScrollRecyclerActivity::class.java))
    }

    fun test4(view: View) {
        startActivity(Intent(this, UITestAct::class.java))
    }

    //测试打开图片的隐士intent
    private fun testIntent() {
        val intent = Intent(Intent.ACTION_VIEW) //打开图片得启动ACTION_VIEW意图

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.iamge_square_yellow)

        val uri: Uri = Uri.parse(
            MediaStore.Images.Media.insertImage(
                contentResolver, bitmap, null,
                null
            )
        ) //将bitmap转换为uri

        intent.setDataAndType(uri, "image/*") //设置intent数据和图片格式

        startActivity(intent)
    }

    private fun testMAC() {
        try {
            Log.d("==========wxzmac1", "${JavaTest.mac1(this)} ")
        } catch (e: Exception) {
            Log.d("==========wxz", "1崩了")
        }

        try {
            Log.d("==========wxzmac2", "${JavaTest.mac2()} ")
        } catch (e: Exception) {
            Log.d("==========wxz", "2崩了")
        }

        try {
            Log.d("==========wxzmac3", "${JavaTest.mac3()} ")
        } catch (e: Exception) {
            Log.d("==========wxz", "3崩了")
        }
        try {
            Log.d("==========wxzmac4", "${JavaTest.mac4()} ")
        } catch (e: Exception) {
            Log.d("==========wxz", "4崩了")
        }
        try {
            Log.d("==========wxzmac5", "${JavaTest.mac5()} ")
        } catch (e: Exception) {
            Log.d("==========wxz", "5崩了")
        }
    }
}
