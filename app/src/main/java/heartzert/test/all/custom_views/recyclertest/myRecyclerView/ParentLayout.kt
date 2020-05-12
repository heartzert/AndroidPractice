package heartzert.test.all.custom_views.recyclertest.myRecyclerView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by heartzert on 2019/10/22.
 * Email: heartzert@163.com
 */
class ParentLayout : FrameLayout, NestedScrollingParent2 {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    private var mChildView: View? = null
    /**
     * 最外层的RecyclerView
     */
    private var mRootList: RecyclerView? = null
    /**
     * 子RecyclerView
     */
    private var mChildList: RecyclerView? = null

    private var mAxes: Int = 0

    fun setChildList(recyclerView: RecyclerView) {
        mChildList = recyclerView
        mChildView = recyclerView
    }

    fun setRootList(recyclerView: RecyclerView) {
        mRootList = recyclerView
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mAxes = axes
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mAxes = View.SCROLL_AXIS_NONE
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
                                type: Int) {

    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (mChildView == null) {
            return
        }
        if (target === mRootList) {
            onParentScrolling(mChildView!!.top, dy, consumed)
            Log.d("========", "onParentScrolling => dy=$dy, consumed=[${consumed[0]},${consumed[1]}]")
        } else {
            onChildScrolling(mChildView!!.top, dy, consumed)
            Log.d("========", "onChildScrolling => dy=$dy, consumed=[${consumed[0]},${consumed[1]}]")
        }
    }

    /**
     * 父列表在滑动
     *
     * @param childTop
     * @param dy
     * @param consumed
     */
    private fun onParentScrolling(childTop: Int, dy: Int, consumed: IntArray) {
        //列表已经置顶
        if (childTop == 0) {
            if (dy > 0 && mChildList != null) {
                //还在向下滑动，此时滑动子列表
                mChildList!!.scrollBy(0, dy)
                consumed[1] = dy
            } else {
                if (mChildList != null && mChildList!!.canScrollVertically(dy)) {
                    consumed[1] = dy
                    mChildList!!.scrollBy(0, dy)
                }
            }
        } else {
            if (childTop < dy) {
                consumed[1] = dy - childTop
            }
        }
    }

    private fun onChildScrolling(childTop: Int, dy: Int, consumed: IntArray) {
        if (childTop == 0) {
            if (dy < 0) {
                //向上滑动
                if (!mChildList!!.canScrollVertically(dy)) {
                    consumed[1] = dy
                    mRootList!!.scrollBy(0, dy)
                }
            }
        } else {
            if (dy < 0 || childTop > dy) {
                consumed[1] = dy
                mRootList!!.scrollBy(0, dy)
            } else {
                //dy大于0
                consumed[1] = dy
                mRootList!!.scrollBy(0, childTop)
            }
        }
    }

    override fun getNestedScrollAxes(): Int {
        return mAxes
    }

//    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        Log.d("========", "onNestedPreScroll => target=$target, dx=$dx, dy=$dy, consumed=$consumed")
//        super.onNestedPreScroll(target, dx, dy, consumed)
//    }
//
//    override fun onStopNestedScroll(target: View, type: Int) {
//        Log.d("========", "onStopNestedScroll => target=$target")
//        super.onStopNestedScroll(target)
//    }
//
//    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
//        Log.d("========", "onStartNestedScroll => target=$target, axes=$axes, type=$type")
//        return true
//    }
//
//    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
//        Log.d("========", "onNestedScrollAccepted => target=$target, axes=$axes, type=$type")
//        super.onNestedScrollAccepted(child, target, axes)
//    }

//    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
//                                type: Int, consumed: IntArray) {
//        Log.d("========",
//            "onNestedScroll => target=$target, dxConsumed=$dxConsumed, dyConsumed=$dyConsumed," +
//                    " dxUnconsumed=$dxUnconsumed, dyUnconsumed=$dyUnconsumed," +
//                    " type=$type, consumed=$consumed")
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
//    }

//    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
//                                type: Int) {
//        Log.d("========",
//            "onNestedScroll => target=$target, dxConsumed=$dxConsumed, dyConsumed=$dyConsumed," +
//                    " dxUnconsumed=$dxUnconsumed, dyUnconsumed=$dyUnconsumed," +
//                    " type=$type")
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
//    }

}