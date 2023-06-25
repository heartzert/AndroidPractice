package heartzert.test.all.custom_views.ScaleableImageView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import heartzert.test.all.R

/**
 * Created by heartzert on 2020-01-12.
 * Email: heartzert@163.com
 */
class ScaleableImageView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {
        const val BIG_SCALE = 2
    }

    val bitmap: Bitmap = (getDrawable(context, R.drawable.test_image) as? BitmapDrawable)?.bitmap!!
    val paint = Paint()
    val gestureDetector = MySimpleOnGestureListener()
    var initScale = 0f
    var translateX = 0f
    var translateY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initScale = if (bitmap.width > bitmap.height) {
            width / bitmap.width.toFloat()
        } else {
            height / bitmap.height.toFloat()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(translateX, translateY)
        canvas?.scale(initScale, initScale, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, paint)
    }

    inner class MySimpleOnGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            translateX = e1.rawX - e2.rawX
            translateY = e1.rawY - e2.rawY
            invalidate()
            return true
        }
    }
}