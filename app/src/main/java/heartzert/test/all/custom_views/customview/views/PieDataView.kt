package heartzert.test.all.custom_views.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2019/4/1.
 * Email: heartzert@163.com
 */
class PieDataView(context: Context): View(context) {

    constructor(context: Context, attributeSet: AttributeSet): this(context)

    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        val rect = RectF(500f, 500f, 1000f, 1000f)
        paint.color = Color.BLUE
        canvas.drawArc(rect, 0f, 23f, true, paint)
        paint.color = Color.RED
        canvas.drawArc(rect, 23f, 95f, true, paint)
        paint.color = Color.GREEN
        canvas.drawArc(rect, 118f, 183f, true, paint)
        paint.color = Color.YELLOW
        canvas.drawArc(rect, 301f, 59f, true, paint)
    }

}