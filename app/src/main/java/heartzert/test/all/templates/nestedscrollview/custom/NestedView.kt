package heartzert.test.all.templates.nestedscrollview.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.NestedScrollingChild2

/**
 * Created by heartzert on 2019/10/29.
 * Email: heartzert@163.com
 */
class NestedView: View, NestedScrollingChild2 {

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
                                      offsetInWindow: IntArray?, type: Int): Boolean {
        Log.d("========",
            "dxConsumed=$dxConsumed, dyConsumed=$dyConsumed, dxUnconsumed=$dxUnconsumed,dyUnconsumed=$dyUnconsumed," +
                    "offsetInWindow=$offsetInWindow, type=$type")
        return true
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return true
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?,
                                         type: Int): Boolean {
        Log.d("========", "dx=$dx, dy=$dy, consumed=$consumed, offsetInWindow=$offsetInWindow")
        return true
    }

    override fun stopNestedScroll(type: Int) {
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return true
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


}