package heartzert.test.all

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import heartzert.test.all.setupactivities.BottomNavigationActivity
import heartzert.test.all.uitest.ButtonActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity

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
//        Log.d("==========", "intent=: $intent")
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, ButtonActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, BottomNavigationActivity::class.java))
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
