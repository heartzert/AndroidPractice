package heartzert.test.all.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RestrictTo.Scope
import heartzert.test.all.R
import kotlinx.coroutines.*

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
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
}
