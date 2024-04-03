package heartzert.test.all

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.*
import android.graphics.Shader.TileMode.CLAMP
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.*
import android.text.style.*
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import heartzert.test.all.R.color
import heartzert.test.all.databinding.ActivityMainBinding
import heartzert.test.all.samples.softkeyboard.SoftKeyboardActivity
import heartzert.test.all.samples.storage_over_11.Storage11Test
import heartzert.test.all.uitest.CommonUITestActivity
import heartzert.test.all.uitest.UITestAct
import heartzert.test.all.uitest.canvas.CanvasActivity
import heartzert.test.all.uitest.canvas.CanvasView
import java.io.File

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(mViewModel)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
            }
        }, IntentFilter("$packageName.alarm"))


        launcher = Storage11Test.registerStartSAF(this)
        Log.d("==========wxz", "${Environment.getExternalStorageDirectory()}");

        text2()
//        startActivity(Intent(this, CanvasActivity::class.java))
    }

    private fun text2() {
        val textView = findViewById<TextView>(R.id.text)
        // 原始文字
        val text = textView.text

        val sourceString = "ceshi daima shishi kan."
        val vipString = "get vip"

        val stringText = sourceString + "  " + vipString
        val spannableStringBuilder = SpannableStringBuilder(stringText)
        val goVipStart = stringText.indexOf(vipString)
        val goVipEnd = goVipStart + vipString.length
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "click", Toast.LENGTH_SHORT).show()
            }
        }
        spannableStringBuilder.setSpan(
            clickableSpan,
            goVipStart,
            goVipEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val colorList = intArrayOf(Color.RED, Color.BLUE)
        val l1 = (sourceString + "  ").length
        val l2 = vipString.length
        val l = stringText.length
//        val x1 = 1f - l2.toFloat() / l.toFloat()
//        val x2 = 1f - (l2.toFloat() / 2) / l.toFloat()
//        Log.d("zyl", "x1 = $x1, x2 = $x2, ${l} , ${l1},${l2}")
        spannableStringBuilder.setSpan(LinearGradientForegroundSpan(colorList, null, l2), goVipStart ,goVipEnd , Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(
            UnderlineSpan(),
            goVipStart,
            goVipEnd,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        textView?.text = spannableStringBuilder
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, CanvasActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, CommonUITestActivity::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, SoftKeyboardActivity::class.java))
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
