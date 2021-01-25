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
import com.google.gson.Gson
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

    class A {
        val B = B()
    }

    class B {
        val string: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.d("==========", "intent=: $intent")


        println("test start:")
        println(Gson().fromJson(Gson().toJson(A()), A::class.java).toString())

//        val uri = Uri.parse("https://media4.giphy.com/media/3oEjHGr1Fhz0kyv8Ig/giphy-preview.gif?cid=9f0f6425b7fd2eb0c1c8a3c89ae84af6efd11ff4183c110c&rid=giphy-preview.gif")
//        println("${uri.scheme}://${uri.host}${uri.path}")
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
