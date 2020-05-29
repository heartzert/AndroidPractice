package heartzert.test.all.samples.nestedscrollview.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.*

/**
 * Created by heartzert on 2019/10/29.
 * Email: heartzert@163.com
 */
class NestedFrameLayout: FrameLayout, NestedScrollingParent2 {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        for (i in 0 until childCount) {
            Log.d("========", "i=$i,target=$target, dx=$dx, dy=$dy, consumed=$consumed, type=$type")

        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
                                type: Int) {
    }
}