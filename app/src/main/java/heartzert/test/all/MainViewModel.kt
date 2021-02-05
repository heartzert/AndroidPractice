package heartzert.test.all

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.provider.AlarmClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

/**
 * Created by heartzert on 2020/12/28.
 * Email: heartzert@163.com
 */
class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 50)
            set(Calendar.SECOND, 0)
        }
        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }
}