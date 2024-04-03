package heartzert.test.all.custom_views.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2019/3/30.
 * Email: heartzert@163.com
 */

class DrawPointView(context: Context) : View(context) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
            .apply {
                color = Color.BLACK
                strokeWidth = 10f

            }

        canvas.drawPoint(2f, 2f, paint)
        canvas.drawPoints(floatArrayOf(500f, 500f, 500f, 700f), paint)

        canvas.drawLine(550f, 550f, 660f, 660f, paint)

        //绘制矩形
        canvas.drawRect(200f, 200f, 300f, 350f, paint)
        val rect = RectF(200f, 400f, 300f, 500f)
        canvas.drawRect(rect, paint)

        //绘制圆角矩形
        //rx，ry为圆角的圆弧的两个半径，圆弧为椭圆弧，不是正圆
        paint.color = Color.RED
        canvas.drawRoundRect(rect, 10f, 10f, paint)
        //当半径正好都为长、宽的一般时，会画出一个椭圆。当rx超过width，ry超过height时，会限制到长、宽的一般。
        paint.color = Color.BLUE
        canvas.drawRoundRect(rect, 100f, 100f, paint)
        paint.color = Color.BLACK

        //绘制椭圆，一个矩形的内切圆
        canvas.drawOval(200f, 600f, 500f, 1000f, paint)

        //绘制圆
        canvas.drawCircle(100f, 1100f, 30f, paint)

        //绘制圆弧 startAngle:起点角度, sweepAngle:划过角度
        //useCenter:是否连接到圆心，若否，则起点终点连接
        canvas.drawArc(200f, 1000f, 350f, 1150f, 45f, 90f, false, paint)
        canvas.drawArc(100f, 1200f, 400f, 1500f, 0f, 90f, true, paint)
    }
}