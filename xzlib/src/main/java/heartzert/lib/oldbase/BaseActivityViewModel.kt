package heartzert.lib.oldbase

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper

/**
 * Created by heartzert on 2018/2/7.
 * Email: heartzert@163.com
 */
open class BaseActivityViewModel(private val mView: BaseView) {

    val context = mView as Context
    val application = context.applicationContext as IApplication
    val TAG = javaClass.name
    //    val log = LogUtils(TAG)
    private var progressDialog: ProgressDialog? = ProgressDialog(context)

    @CallSuper
    open fun onCreate() {
    }

    @CallSuper
    open fun onStart() {
    }

    @CallSuper
    open fun onNewIntent(intent: Intent?) {
    }

    @CallSuper
    open fun onRestart() {
    }

    @CallSuper
    open fun onResume() {
    }

    @CallSuper
    open fun onPause() {
    }

    @CallSuper
    open fun onStop() {
    }

    @CallSuper
    open fun onDestroy() {
        dismissProgressDialog()
        progressDialog = null
    }

    @CallSuper
    open fun onBackPressed() {
        mView.finishActivity()
    }

    @CallSuper
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    @CallSuper
    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    }

    protected fun getString(resId: Int): String = application.getString(resId)

    protected fun showProgressDialog() {
        if (progressDialog?.isShowing == false) {
            progressDialog?.show()
        }
    }

    protected fun dismissProgressDialog() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

//    /**
//     * protected logs, do not need change
//     */
//    protected fun logv(message: String?) = log.logv(message)
//    protected fun logi(message: String?) = log.logi(message)
//    protected fun logd(message: String?) = log.logd(context.applicationContext, message)
//    protected fun logw(message: String?) = log.logw(message)
//    protected fun logw(message: String?, throwable: Throwable?) = log.logw(message, throwable)
//    protected fun loge(message: String?) = log.loge(message)
//    protected fun loge(message: String?, throwable: Throwable?) = log.loge(message, throwable)
//    protected fun logwtf(message: String?) = log.logwtf(message)
//    protected fun logwtf(message: String?, throwable: Throwable?) = log.logwtf(message, throwable)
//
//    /**
//     * should use with toastutils
//     */
//    protected fun shortShowToast(message: String?) {
//        ToastUtil.show(context, message)
//    }
//
//    protected fun longShowToast(message: String?) {
//        ToastUtil.show(context, message)
//    }
}