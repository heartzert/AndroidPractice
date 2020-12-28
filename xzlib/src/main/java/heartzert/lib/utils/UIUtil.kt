package heartzert.lib.utils

import android.content.res.Resources
import android.graphics.Camera
import android.graphics.Rect
import android.util.Log
import android.view.TouchDelegate
import android.view.View

/**
 * Created by heartzert on 2019/5/24.
 * Email: heartzert@163.com
 */

/**
 * 更新view的layout params
 */
fun View.updateLayoutParam() {
    this.layoutParams = this.layoutParams
}

/**
 * 设置view是否可见
 */
fun View.SetVisible(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 设置View的Camera的z轴高度
 */
fun Camera.setLocationZ(float: Float) {
    setLocation(0f, 0f, float)
}

/**
 * 重置Camera的Z轴
 */
fun Camera.fitLoacationZ() {
    setLocationZ(-8 * Resources.getSystem().displayMetrics.density)
}

/**
 * Expand the click area of ​​the view
 *
 * @param view       The view.
 * @param expandSize The size.
 */
fun View.expandClickArea(expandSize: Int) {
    expandClickArea(expandSize, expandSize, expandSize, expandSize)
}

fun View.expandClickArea(
    expandSizeTop: Int,
    expandSizeLeft: Int,
    expandSizeRight: Int,
    expandSizeBottom: Int
) {
    val parentView = parent as? View
    parentView ?: kotlin.run{
        Log.e("ClickUtils", "expandClickArea must have parent view.")
        return
    }
    parentView.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= expandSizeTop
        rect.bottom += expandSizeBottom
        rect.left -= expandSizeLeft
        rect.right += expandSizeRight
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}