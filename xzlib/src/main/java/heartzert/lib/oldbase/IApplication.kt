package heartzert.lib.oldbase

import android.app.Application
import android.content.Context

/**
 * Created by heartzert on 2019/4/16.
 * Email: heartzert@163.com
 */
open class IApplication: Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var application: Application

        fun init(application: Application) {
            appContext = application.applicationContext
            Companion.application = application
        }
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}