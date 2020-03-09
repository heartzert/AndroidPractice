package heartzert.lib.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */

/**
 * 获得屏幕高度
 *
 * @return
 */
fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

/**
 * 获得屏幕宽度
 *
 * @return
 */
fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

/**
 * 获得状态栏的高度
 *
 * @return
 */
fun getStatusHeight(): Int {

    var statusHeight = -1
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val `object` = clazz.newInstance()
        val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
        statusHeight = Resources.getSystem().getDimensionPixelSize(height)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return statusHeight
}

/**
 * 获取当前屏幕截图，包含状态栏
 *
 * @param activity
 * @return
 */
fun snapShotWithStatusBar(activity: Activity): Bitmap? {
    val view = activity.window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val width = getScreenWidth()
    val height = getScreenHeight()
    var bp: Bitmap? = null
    bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
    view.destroyDrawingCache()
    return bp

}

/**
 * 获取当前屏幕截图，不包含状态栏
 *
 * @param activity
 * @return
 */
fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
    val view = activity.window.decorView
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache()
    val bmp = view.drawingCache
    val frame = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top
    val width = getScreenWidth()
    val height = getScreenHeight()
    var bp: Bitmap? = null
    bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
    view.destroyDrawingCache()
    return bp
}

/**
 * dp转px
 *
 * @param context
 * @param dpVal
 * @return
 */
fun dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dpVal, Resources.getSystem().displayMetrics).toInt()
}

/**
 * sp转px
 *
 * @param spVal
 * @return
 */
fun sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
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

/**
 * 动态设置图片宽高
 */
fun getBitmapConfiguration(bitmap: Bitmap?, imageView: ImageView, screenRadio: Float): FloatArray {
    val screenWidth = getScreenWidth()
    var rawWidth = 0f
    var rawHeight = 0f
    var width = 0f
    var height = 0f
    if (bitmap == null) {
        width = screenWidth / screenRadio
        height = width
        imageView.scaleType = ImageView.ScaleType.FIT_XY
    } else {
        rawWidth = bitmap.width.toFloat()
        rawHeight = bitmap.height.toFloat()
        if (rawHeight > 10 * rawWidth) {
            imageView.scaleType = ImageView.ScaleType.CENTER
        } else {
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
        val radio = rawHeight / rawWidth
        width = screenWidth / screenRadio
        height = radio * width
    }
    return floatArrayOf(width, height)
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
