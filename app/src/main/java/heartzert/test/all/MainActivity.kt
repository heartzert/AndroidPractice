package heartzert.test.all

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.custom_views.ScaleableImageView.ScaleableImageTestActivity
import heartzert.test.all.templates.handlerthread.HandlerThreadActivity
import heartzert.test.all.templates.intentservice.IntentServiceActivity
import heartzert.test.all.templates.motionLayout.MotionLayoutActivity
import heartzert.test.all.templates.reflaction.ReflactionActivity
import heartzert.test.all.templates.retrofit.RetrofitActivity
import heartzert.test.all.uitest.UITestAct

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
