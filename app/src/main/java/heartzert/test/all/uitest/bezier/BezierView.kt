package heartzert.test.all.uitest.bezier

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import heartzert.lib.utils.dp
import heartzert.lib.utils.square
import heartzert.lib.utils.squareRoot

/**
 * Created by heartzert on 2/20/21.
 * Email: heartzert@163.com
 */
class BezierView : View {

    companion object {
        //触摸范围
        private val TOUCH_AREA = 2.dp()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val pointList = mutableListOf<Array<Float>>()
    private var moving = -1

    private val pointPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10.dp().toFloat()
    }

    private val pathPaint = Paint().apply {
        color = Color.CYAN
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val positionX = MeasureSpec.getSize(widthMeasureSpec).toFloat() / 2
        val positionY = MeasureSpec.getSize(heightMeasureSpec).toFloat() / 2
        addPoint(positionX, positionY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        pointList.forEach {
            canvas?.drawPoint(it[0], it[1], pointPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                moving = around(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (moving != -1) {
                    pointList[moving][0] = event.x
                    pointList[moving][1] = event.y
                    invalidate()
                }
                return false
            }
            MotionEvent.ACTION_UP -> {
                if (moving != -1) {
                    moving = -1
                } else {
                    addPoint(event.x, event.y)
                }
                return false
            }
        }
        return super.onTouchEvent(event)
    }

    private fun addPoint(x: Float, y: Float) {
        pointList.add(arrayOf(x, y))
        invalidate()
    }

    private fun around(event: MotionEvent): Int {
        pointList.forEachIndexed { index: Int, it: Array<Float> ->
            if (around(event, it)) {
                return index
            }
        }
        return -1
    }

    private fun around(event: MotionEvent, point: Array<Float>): Boolean {
        return ((event.x - point[0]).square() + (event.y - point[1]).square()).squareRoot() - pointPaint.strokeWidth <= TOUCH_AREA
    }

}