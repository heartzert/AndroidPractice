package heartzert.test.all

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.samples.intentservice.IntentServiceActivity
import heartzert.test.all.samples.retrofit.RetrofitActivity
import heartzert.test.all.setupactivities.BottomNavigationActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, RetrofitActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, BottomNavigationActivity::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, ScrollRecyclerActivity::class.java))
    }

}
