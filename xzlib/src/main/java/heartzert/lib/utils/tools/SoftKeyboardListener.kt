package heartzert.lib.utils.tools

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.SoftReference

/**
 * Created by heartzert on 3/24/21.
 * Email: heartzert@163.com
 */
//监听键盘弹出收起和键盘高度
class SoftKeyboardListener(
    activity: Activity,
    private var mCallback: Callback? = null
) : LifecycleObserver {

    companion object {

        //键盘打开状态
        private const val MODE_OPEN = 0

        //键盘关闭状态
        private const val MODE_CLOSE = 1

        //有些机型相减后是1，所以判断搞大点做兼容
        private const val minHeight = 10
    }

    //保存当前的activity
    private var mActivity = SoftReference(activity)

    //绑定的view
    private var mView: View? = null

    private var mGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {

        private var lastMode = MODE_CLOSE

        override fun onGlobalLayout() {
            mActivity.get() ?: return
            mView ?: return

            //获取当前窗口实际的可见区域
            val screenSize = Point()
            mActivity.get()!!.windowManager.defaultDisplay.getSize(screenSize)

            val r = Rect()
            mView!!.getWindowVisibleDisplayFrame(r)
            //两次窗口高度相减，就是软键盘高度
            val softKeyboardHeight = screenSize.y - r.height()

            //防重入，
            val thisMode = if (softKeyboardHeight > minHeight) MODE_OPEN else MODE_CLOSE
            if (thisMode == lastMode) return
            lastMode = thisMode

            if (softKeyboardHeight > minHeight) {
                Log.d("====wxz====","softKeyboardHeight up: $softKeyboardHeight")
                mCallback?.onKeyboardUp(softKeyboardHeight)
            } else {
                Log.d("====wxz====", "softKeyboardHeight down")
                mCallback?.onKeyboardDown()
            }
        }
    }

    interface Callback {
        fun onKeyboardUp(softKeyboardHeight: Int)

        fun onKeyboardDown()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        mActivity.get()?.apply {
            window.decorView.viewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mActivity.get()?.apply {
            window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(mGlobalLayoutListener)
        }
    }

    /**
     * 绑定view
     */
    fun bind(view: View?) {
        view ?: return
        mView = view
        mView!!.viewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener)
    }

    /**
     * 解绑view
     */
    fun unBind() {
        mView ?: return
        mView!!.viewTreeObserver.removeOnGlobalLayoutListener(mGlobalLayoutListener)
        mView = null
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }
}