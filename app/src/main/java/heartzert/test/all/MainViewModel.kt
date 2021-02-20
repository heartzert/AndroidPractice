package heartzert.test.all

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by heartzert on 2020/12/28.
 * Email: heartzert@163.com
 */
class MainViewModel(val mApplication: Application) : AndroidViewModel(mApplication),
    LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        //date = getNextDay(date, MAX_LONG_TIME_NO_USE_DAYS);
        //date = getNextHour(date, NOTIFICATION_HOUR_TIMESTAMP);
        //date = getNextMinute(date, 1);

        val request = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setInitialDelay(15, TimeUnit.SECONDS).build()

        WorkManager.getInstance(mApplication).enqueue(request)

//        val cld = Calendar.getInstance()
//        cld.time = clearTime(Date())
//        cld.add(Calendar.SECOND, 30)
//        val date = cld.time

//        val alarmManager =
//            getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        val intent = Intent(getApplication<Application>().packageName + ".alarm")
//        intent.setPackage(getApplication<Application>().packageName)
//        intent.putExtra("param", 1234)
//        val pendingIntent = PendingIntent.getBroadcast(
//            getApplication(), 1234, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        alarmManager.set(AlarmManager.RTC, date.time, pendingIntent)
    }

    private fun clearTime(date: Date): Date {
        val c = Calendar.getInstance()
        c.time = date
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        return c.time
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }
}

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
//        Toast.makeText(applicationContext, "wocao", Toast.LENGTH_LONG).show()
        Log.d("==========wxz", "" + System.currentTimeMillis())

        return Result.success()
    }
}