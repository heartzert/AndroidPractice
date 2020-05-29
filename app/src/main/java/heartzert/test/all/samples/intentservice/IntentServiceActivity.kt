package heartzert.test.all.samples.intentservice

import android.app.IntentService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import heartzert.test.all.R

class IntentServiceActivity : AppCompatActivity() {

    private lateinit var mIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)
        mIntent = Intent(this, MyIntentService::class.java)
        startService(mIntent)
        startService(mIntent)
        startService(mIntent)
    }

    fun send(view: View) {
        startService(mIntent)
        startService(mIntent)
    }

    class MyIntentService: IntentService("MyIntentService") {

        override fun onHandleIntent(intent: Intent?) {
            Thread.sleep(1000)
            Log.d("========", "thread=${Thread.currentThread().name}")
            Log.d("========", "thread=${Thread.currentThread()}")
        }
    }
}

