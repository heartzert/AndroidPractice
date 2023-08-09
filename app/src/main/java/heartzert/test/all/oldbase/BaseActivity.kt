package heartzert.test.all.oldbase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by heartzert on 2018/2/7.
 * Email: heartzert@163.com
 *
 * 使用流程：
 * 0.需要配合BaseActivityViewModel、BaseView、LogUtils使用
 * 1.实现getLayout（只需返回布局id）
 * 2.实现setViewModel，在其中调用setViewModel(int: Int, viewModel: ViewModel)方法
 * 3.实现initToolbar()
 */
abstract class BaseActivity<ViewModel : BaseActivityViewModel, Binding : ViewDataBinding> : AppCompatActivity(),
    BaseView {

    lateinit var binding: Binding
    var viewModel: ViewModel? = null
    val TAG = javaClass.name
//    val log = LogUtils(TAG)
//    private val exitReceiver = ExitAppReceiver()

    companion object {
        //自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
        private const val EXIT_APP_ACTION = "com.youmeid.exit_app"

        fun exitApp(context: Context) {
            val intent = Intent()
            intent.action = EXIT_APP_ACTION
            context.sendBroadcast(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        logd("onCreate")
        binding = DataBindingUtil.setContentView(this, getLayout()) as Binding
//        registerExitReceiver()
        setViewModel()
        initToolbar()
        viewModel?.onCreate()
    }

    override fun onStart() {
//        logd("onStart")
        viewModel?.onStart()
        super.onStart()
    }

    override fun onResume() {
//        logd("onResume")
        viewModel?.onResume()
        super.onResume()
    }

    override fun onPause() {
//        logd("onPause")
        viewModel?.onPause()
        super.onPause()
    }

    override fun onStop() {
//        logd("onStop")
        viewModel?.onStop()
        super.onStop()
    }

    override fun onNewIntent(intent: Intent?) {
//        logd("onNewIntent")
        viewModel?.onNewIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        logd("onActivityResult")
        viewModel?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        logd("onRequestPermissionResult")
        viewModel?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onRestart() {
//        logd("onRestart")
        viewModel?.onRestart()
        super.onRestart()
    }

    override fun onBackPressed() {
//        logd("onBackPressed")
        viewModel?.onBackPressed()
    }

    override fun onDestroy() {
//        logd("onDestroy")
        viewModel?.onDestroy()
        viewModel = null
//        unRegisterExitReceiver()
        super.onDestroy()
    }

    /**
     * @param BR.int xml文件中variable的alia
     */
    protected fun setViewModel(int: Int, viewModel: ViewModel) {
        binding.setVariable(int, viewModel)
        this.viewModel = viewModel
    }

    /**
     * set layout when use this base class
     */
    abstract fun getLayout(): Int

    /**
     * set viewModel
     */
    abstract fun setViewModel()

    override fun finishActivity() = finish()

    override fun initToolbar() {}

    override fun isActivityFinishing(): Boolean = this.isFinishing

    override fun getActivity(): Activity = this


    /**
     * 设置透明状态栏
     */
    override fun setTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = this.window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            this.window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun getActivityIntent(): Intent = intent

    override fun setActivityIntent(intent: Intent?) {
        this.intent = intent
    }

    override fun setActivityResult(resultCode: Int, intent: Intent?, ifFinishActivity: Boolean) {
        setResult(resultCode, intent)
        if (ifFinishActivity) {
            finish()
        }
    }

    override fun setActivityEmptyResult(resultCode: Int, ifFinishActivity: Boolean) {
        setResult(resultCode)
        if (ifFinishActivity) {
            finish()
        }
    }

//    /**
//     * for force exit app
//     */
//    private fun registerExitReceiver() {
//        val exitFilter = IntentFilter()
//        exitFilter.addAction(EXIT_APP_ACTION)
//        registerReceiver(exitReceiver, exitFilter)
//    }
//
//    /**
//     * for force exit app
//     */
//    private fun unRegisterExitReceiver() {
//        unregisterReceiver(exitReceiver)
//    }
//
//    /**
//     * protected logs, don't change
//     */
//    protected fun logv(message: String?) = log.logv(message)
//
//    protected fun logi(message: String?) = log.logi(message)
//
//    protected fun logd(message: String?) = log.logd(applicationContext, message)
//
//    protected fun logw(message: String?) = log.logw(message)
//
//    protected fun logw(message: String?, throwable: Throwable?) = log.logw(message, throwable)
//
//    protected fun loge(message: String?) = log.loge(message)
//
//    protected fun loge(message: String?, throwable: Throwable?) = log.loge(message, throwable)
//
//    protected fun logwtf(message: String?) = log.logwtf(message)
//
//    protected fun logwtf(message: String?, throwable: Throwable?) = log.logwtf(message, throwable)
}