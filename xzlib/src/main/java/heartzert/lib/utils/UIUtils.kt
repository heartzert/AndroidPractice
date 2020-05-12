package heartzert.lib.utils

import android.content.res.Resources
import android.view.View

/**
 * Created by heartzert on 2019/5/24.
 * Email: heartzert@163.com
 */

/**
 * 获取statusBar高度
 */
fun getStatusBarHeight(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 更新view的layout params
 */
fun View.updateLayoutParam() {
    this.layoutParams = this.layoutParams
}