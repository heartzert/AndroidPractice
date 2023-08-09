package heartzert.test.all.oldbase

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by heartzert on 2018/6/12.
 * Email: heartzert@163.com
 */

abstract class BaseActivityNoVM : AppCompatActivity() {

    val TAG = javaClass.name
//    val log = LogUtils(TAG)
//    private val exitReceiver = ExitAppReceiver()

    companion object {
        //自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
        const val EXIT_APP_ACTION = "com.youmeid.exit_app"

        fun exitApp(context: Context) {
            val intent = Intent()
            intent.action = EXIT_APP_ACTION
            context.sendBroadcast(intent)
        }
    }

    //abstract
    abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
//        logd("onCreate")
        beforeCreate()
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
//        registerExitReceiver()
        afterCreate()
    }

//    override fun onStart() {
//        logd("onStart")
//        super.onStart()
//    }
//
//    override fun onResume() {
//        logd("onResume")
//        super.onResume()
//    }
//
//    override fun onPause() {
//        logd("onPause")
//        super.onPause()
//    }
//
//    override fun onStop() {
//        logd("onStop")
//        super.onStop()
//    }

//    override fun onNewIntent(intent: Intent?) {
//        logd("onNewIntent")
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        logd("onActivityResult")
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        logd("onRequestPermissionResult")
//    }

//    override fun onRestart() {
//        logd("onRestart")
//        super.onRestart()
//    }

//    override fun onBackPressed() {
//        logd("onBackPressed")
//    }

//    override fun onDestroy() {
//        logd("onDestroy")
//        unRegisterExitReceiver()
//        super.onDestroy()
//    }

    open fun afterCreate() {}
    open fun beforeCreate() {}

    /**
     * 设置透明状态栏
     */
    fun setTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = this.window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            this.window.statusBarColor = Color.TRANSPARENT
        }
    }
//
//    private fun registerExitReceiver() {
//        val exitFilter = IntentFilter()
//        exitFilter.addAction(EXIT_APP_ACTION)
//        registerReceiver(exitReceiver, exitFilter)
//    }
//
//    private fun unRegisterExitReceiver() {
//        unregisterReceiver(exitReceiver)
//    }
//
//    /**
//     * protected logs, do not need change
//     */
//    protected fun logv(message: String?) = log.logv(message)
//
//    protected fun logi(message: String?) = log.logi(message)
//    protected fun logd(message: String?) = log.logd(applicationContext, message)
//    protected fun logw(message: String?) = log.logw(message)
//    protected fun logw(message: String?, throwable: Throwable?) = log.logw(message, throwable)
//    protected fun loge(message: String?) = log.loge(message)
//    protected fun loge(message: String?, throwable: Throwable?) = log.loge(message, throwable)
//    protected fun logwtf(message: String?) = log.logwtf(message)
//    protected fun logwtf(message: String?, throwable: Throwable?) = log.logwtf(message, throwable)
}