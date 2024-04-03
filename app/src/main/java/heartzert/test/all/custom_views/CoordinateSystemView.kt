package heartzert.test.all.custom_views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef

/**
 * Created by heartzert on 3/8/21.
 * Email: heartzert@163.com
 */
class CoordinateSystemView: View {

    @IntDef(CoordinateMode.MODE_ABSOLUTE, CoordinateMode.MODE_RELATIVE)
    annotation class CoordinateMode {
        companion object {
            /**
             * 绝对坐标
             */
            const val MODE_ABSOLUTE = 1

            /**
             * 相对坐标
             */
            const val MODE_RELATIVE = 2
        }
    }

    @CoordinateSystemView.CoordinateMode
    var mode = CoordinateMode.MODE_ABSOLUTE

    private var mW = 0
    private var mH = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mW = w
        mH = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

}