package heartzert.test.all.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2019/4/1.
 * Email: heartzert@163.com
 */
class CanvasTestView(context: Context) : View(context) {

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        val paint = Paint()
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
    }
}