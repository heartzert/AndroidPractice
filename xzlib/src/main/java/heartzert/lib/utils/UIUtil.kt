package heartzert.lib.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2019/5/24.
 * Email: heartzert@163.com
 */


val Int.dp
    get() = dp2px(this.toFloat())

val Float.dp
    get() = dp2px(this)

val Int.sp
    get() = sp2px(this.toFloat())

val Float.sp
    get() = sp2px(this)

/**
 * 更新view的layout params
 */
fun View.updateLayoutParam() {
    this.layoutParams = this.layoutParams
}

/**
 * 设置view是否可见
 */
fun View.setVisible(visible: Boolean) {
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

/**
 * 设置RecyclerView的item decoration
 */
fun RecyclerView.addItemDecoration(left: Int, top: Int, right: Int, bottom: Int) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = top
            outRect.left = left
            outRect.right = right
            outRect.bottom = bottom
        }
    })
}


/**
 * 扩展点击区域的范围
 *
 * @param view       需要扩展的元素，此元素必需要有父级元素
 * @param expendSize 需要扩展的尺寸（以sp为单位的）
 */
fun View.expendTouchArea(expendSize: Int) {
    val parentView = this.parent as? View ?: return
    parentView.post {
        val rect = Rect()
        //如果太早执行本函数，会获取rect失败，因为此时UI界面尚未开始绘制，无法获得正确的坐标
        this.getHitRect(rect)
        rect.left -= expendSize
        rect.top -= expendSize
        rect.right += expendSize
        rect.bottom += expendSize
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}