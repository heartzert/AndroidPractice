package heartzert.test.all.uitest.bezier

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val pointList = mutableListOf<Array<Float>>()

    //是否未点中已存在的点
    private var newDown = -1

    //单词点击是否移动
    private var moved = false

    //触摸范围
    private val touchArea = 10.dp()
    private val pointPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5.dp().toFloat()
    }

    private val pathPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2.dp().toFloat()
    }

    private val path = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (pointList.size > 0) return
        val positionX = MeasureSpec.getSize(widthMeasureSpec).toFloat() / 3
        val positionY = MeasureSpec.getSize(heightMeasureSpec).toFloat() / 2
        addPoint(positionX, positionY)
        addPoint(positionX * 2, positionY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (pointList.isNullOrEmpty()) return
        pointList.forEach {
            canvas?.drawCircle(it[0], it[1], 2.dp().toFloat(), pointPaint)
        }
        if (pointList.size <= 2) return
        path.reset()
        val point0 = pointList[0]
        val point1 = pointList[1]
        val point2 = pointList[2]
        val point3 = pointList.getOrNull(3)
        path.moveTo(point0[0], point0[1])
        if (point3 == null) {
            path.quadTo(point2[0], point2[1], point1[0], point1[1])
        } else {
            path.cubicTo(point2[0], point2[1], point3[0], point3[1], point1[0], point1[1])
        }

        canvas?.drawPath(path, pathPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                newDown = around(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (newDown != -1) {
                    pointList[newDown][0] = event.x
                    pointList[newDown][1] = event.y
                    invalidate()
                }
                moved = true
                return false
            }
            MotionEvent.ACTION_UP -> {
                if (newDown != -1) {
                    newDown = -1
                } else if (!moved) {
                    if (pointList.size < 4) {
                        addPoint(event.x, event.y)
                    }
                }
                moved = false
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
        return ((event.x - point[0]).square() + (event.y - point[1]).square()).squareRoot() - pointPaint.strokeWidth <= touchArea
    }

}