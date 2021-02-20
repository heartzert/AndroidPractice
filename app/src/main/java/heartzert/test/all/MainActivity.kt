package heartzert.test.all

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import heartzert.test.all.setupactivities.BottomNavigationActivity
import heartzert.test.all.uitest.ButtonActivity
import heartzert.test.all.uitest.CommonUITestActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity
import heartzert.test.all.uitest.bubbles.BubblesTestActivity
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit

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

//        Log.d("==========", "intent=: $intent")

//        val uri = Uri.parse("https://media4.giphy.com/media/3oEjHGr1Fhz0kyv8Ig/giphy-preview.gif?cid=9f0f6425b7fd2eb0c1c8a3c89ae84af6efd11ff4183c110c&rid=giphy-preview.gif")
//        println("${uri.scheme}://${uri.host}${uri.path}")
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
}
