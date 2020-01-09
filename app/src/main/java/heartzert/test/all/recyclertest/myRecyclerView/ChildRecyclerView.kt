package heartzert.test.all.recyclertest.myRecyclerView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParent3
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2019/10/22.
 * Email: heartzert@163.com
 */
class ChildRecyclerView: LinearLayout, NestedScrollingChild3 {

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
                                      offsetInWindow: IntArray?, type: Int, consumed: IntArray) {
        Log.d("========",
            "dispatchNestedScroll => dxConsumed=$dxConsumed, dyConsumed=$dyConsumed," +
                    " dxUnconsumed=$dxUnconsumed, dyUnconsumed=$dyUnconsumed," +
                    " offsetInWindow=$offsetInWindow, type=$type, consumed=$consumed")

        super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
                                      offsetInWindow: IntArray?, type: Int): Boolean {
        Log.d("========",
            "dispatchNestedScroll => dxConsumed=$dxConsumed, dyConsumed=$dyConsumed," +
                    " dxUnconsumed=$dxUnconsumed, dyUnconsumed=$dyUnconsumed," +
                    " offsetInWindow=$offsetInWindow, type=$type")
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.d("========", "startNestedScroll => axes=$axes, type=$type")
        return super.startNestedScroll(axes)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?,
                                         type: Int): Boolean {
        Log.d("========",
            "dispatchNestedPreScroll => dx=$dx, dy=$dy, consumed=$consumed, offsetInWindow=$offsetInWindow, type=$type")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun stopNestedScroll(type: Int) {
        Log.d("========", "stopNestedScroll")
        super.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        Log.d("========", "stopNestedScroll")
        return super.hasNestedScrollingParent()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

}