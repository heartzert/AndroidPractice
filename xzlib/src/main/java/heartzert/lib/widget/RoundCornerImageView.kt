package heartzert.lib.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Path.Direction.CW
import android.graphics.RectF
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import heartzert.lib.R

/**
 * Created by heartzert on 2020/10/28.
 * Email: heartzert@163.com
 */
class RoundCornerImageView: AppCompatImageView {

    private var cornerRadius = -1f
    private val mClipPath = Path()
    private var rect: RectF? = RectF()


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//        cornerRadius =
//            getCornerRadius(context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
        defStyleAttr) {
//        cornerRadius =
//            getCornerRadius(context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView))
    }

//    private fun getCornerRadius(array: TypedArray): Float {
//        return array.getDimension(R.styleable.RoundCornerImageView_cornerRadius, cornerRadius)
//    }

    override fun onDraw(canvas: Canvas) {
        if (isInEditMode) {
            super.onDraw(canvas)
            return
        }
        val w = this.width
        val h = this.height
        if (cornerRadius < 0) {
            cornerRadius = 8.0f
        }
        val fx = cornerRadius
        if (rect == null) {
            rect = RectF()
        }
        rect!!.left = 0f
        rect!!.top = 0f
        rect!!.right = w.toFloat()
        rect!!.bottom = h.toFloat()
        mClipPath.addRoundRect(rect, fx, fx, CW)
        canvas.clipPath(mClipPath)
        try {
            super.onDraw(canvas)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}