package heartzert.lib

import android.app.Application
import android.content.Context

/**
 * Created by heartzert on 2019/4/16.
 * Email: heartzert@163.com
 */
class IApplication: Application() {
    companion object {
        var appContext: Context? = null
        var application: Application? = null

        fun init(application: Application) {
            appContext = application
            Companion.application = application
        }
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}