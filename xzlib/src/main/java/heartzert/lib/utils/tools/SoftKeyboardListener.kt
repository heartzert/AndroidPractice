package heartzert.lib.utils.tools

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.lang.ref.SoftReference

/**
 * Created by heartzert on 3/24/21.
 * Email: heartzert@163.com
 */
class SoftKeyboardListener(
    activity: Activity,
    private var mCallback: Callback? = null
) : LifecycleObserver {

    private var mActivity = SoftReference(activity)

    private var mWindowHeight = 0

    private var mGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        //获取当前窗口实际的可见区域
        val r = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(r)
        val height: Int = r.height()
        if (mWindowHeight == 0) {
            //一般情况下，这是原始的窗口高度
            mWindowHeight = height
        } else {
            if (mWindowHeight != height) {
                //两次窗口高度相减，就是软键盘高度
                val softKeyboardHeight = mWindowHeight - height
                mCallback?.onKeyboardUp(softKeyboardHeight)
            } else {
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

    fun setCallback(callback: Callback) {
        mCallback = callback
    }
}