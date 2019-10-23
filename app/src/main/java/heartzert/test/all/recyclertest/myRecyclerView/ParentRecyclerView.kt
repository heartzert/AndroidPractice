package heartzert.test.all.recyclertest.myRecyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.NestedScrollingParent2
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2019/10/22.
 * Email: heartzert@163.com
 */
class ParentRecyclerView: RecyclerView, NestedScrollingParent2 {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,
        defStyle)


    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
    }

    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                                dyUnconsumed: Int, type: Int) {
    }
}