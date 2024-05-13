package heartzert.test.all.samples.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import heartzert.test.all.R
import kotlinx.coroutines.*

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        val job = lifecycleScope.launch(start = CoroutineStart.LAZY) {
            delay(4000)
            Log.d("=====wxz=====", "onCreate: ")
        }.apply {
            invokeOnCompletion {
                Log.d("=====wxz=====", "onCreate: invokeOnCompletion. isfinish:${it == null}, iscancelled: ${it is CancellationException}")
            }
        }
        job.start()
    }

    suspend fun getName() {
        CoroutineScope(Dispatchers.Main).launch {

        }
    }

    suspend fun getAge() {
        GlobalScope.launch(Dispatchers.Main) {

        }
    }

    suspend fun getSex() {
        MainScope().launch {

        }
    }

    suspend fun getCountry() {
        coroutineScope {

        }
    }

    suspend fun getBirth() {
        withContext(Dispatchers.Unconfined) {

        }
    }

    suspend fun getBb() {
        lifecycleScope.launchWhenResumed {

        }
    }
}
