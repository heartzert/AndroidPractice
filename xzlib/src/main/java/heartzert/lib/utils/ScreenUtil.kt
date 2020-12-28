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
 * 获取statusBar高度
 */
fun getStatusBarHeight(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 获取navigationBar高度
 */
private fun getNavigationBarHeight(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

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

