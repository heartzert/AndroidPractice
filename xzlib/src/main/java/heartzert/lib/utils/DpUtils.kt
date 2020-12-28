package heartzert.lib.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by heartzert on 2020/12/14.
 * Email: heartzert@163.com
 */

fun Int.dp() = dp2px(this.toFloat())

fun Float.dp() = dp2px(this)

fun Int.sp() = sp2px(this.toFloat())

fun Float.sp() = sp2px(this)

/**
 * dp转px
 *
 * @param context
 * @param dpVal
 * @return
 */
fun dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, Resources.getSystem().displayMetrics).toInt()
}

/**
 * sp转px
 *
 * @param spVal
 * @return
 */
fun sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spVal, Resources.getSystem().displayMetrics).toInt()
}

/**
 * px转dp
 *
 * @param pxVal
 * @return
 */
fun px2dp(pxVal: Float): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return pxVal / scale
}

/**
 * px转sp
 *
 * @param pxVal
 * @return
 */
fun px2sp(pxVal: Float): Float {
    val scale = Resources.getSystem().displayMetrics.scaledDensity
    return pxVal / scale
}