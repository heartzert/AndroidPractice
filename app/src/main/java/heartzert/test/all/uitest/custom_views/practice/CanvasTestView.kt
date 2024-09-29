package heartzert.test.all.uitest.custom_views.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style.STROKE
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2019/4/1.
 * Email: heartzert@163.com
 */
class CanvasTestView(context: Context) : View(context) {

    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private var mWidth: Int? = null
    private var mHeight: Int? = null

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        this.canvas = canvas
        paint = Paint().apply {
            strokeWidth = 10f
            color = Color.BLUE
            this.style = STROKE
        }
        canvas.translate(mWidth!! / 2f, mHeight!! / 2f)

//        testTranslate()
//        testScale()
        testRotate()
    }

    private fun drawXY(paint: Paint) {
        canvas.drawLine(mWidth!!.inv().toFloat(), mWidth!!.toFloat(), mHeight!!.inv().toFloat(), mHeight!!.toFloat(),
            paint)
    }

    private fun testTranslate() {
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
    }

    private fun testScale() {
        paint.color = Color.RED
        val rec = RectF(0f, 0f, 200f, 200f)
        canvas.drawRect(rec, paint)

        paint.color = Color.YELLOW
        canvas.scale(-0.2f, -0.3f)
        canvas.drawRect(rec, paint)

        paint.color = Color.GREEN
        canvas.scale(-2f, -2f)
        canvas.drawRect(rec, paint)

        paint.color = Color.BLUE
        canvas.scale(0.5f, 0.5f, 100f, 100f)
        canvas.drawRect(rec, paint)
    }

    private fun testRotate() {
        paint.color = Color.RED
        val rec = RectF(0f, 0f, 200f, 200f)
        canvas.drawRect(rec, paint)


        for (i in 1..360 step 20) {
            canvas.rotate(20f)
            canvas.drawRect(rec, paint)
        }
    }
}