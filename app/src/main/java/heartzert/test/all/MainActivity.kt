package heartzert.test.all

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.samples.intentservice.IntentServiceActivity
import heartzert.test.all.samples.reflaction.ReflactionActivity
import heartzert.test.all.samples.retrofit.RetrofitActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun firstTest(view: View) {
        startActivity(Intent(this, RetrofitActivity::class.java))
    }

    fun secondTest(view: View) {
        startActivity(Intent(this, IntentServiceActivity::class.java))
    }

    fun thirdTest(view: View) {
        startActivity(Intent(this, ReflactionActivity::class.java))
    }

}
