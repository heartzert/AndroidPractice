package heartzert.test.all.customview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import heartzert.lib.unAbs

/**
 * Created by heartzert on 2019/4/1.
 * Email: heartzert@163.com
 */
class CanvasTestView(context: Context) : View(context) {

    private lateinit var canvas: Canvas
    private var mWidth: Int? = null
    private var mHeight: Int? = null

    constructor(context: Context, attributeSet: AttributeSet) : this(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        this.canvas = canvas
        val paint = Paint()
        val i = 5
        for (x in 0..100) {
            Log.d("123123123", unAbs(x).toString())
        }
//        testTranslate(paint)

    }

    private fun drawXY(paint: Paint) {
        canvas.drawLine(mWidth!!.inv().toFloat(), mWidth!!.toFloat(), mHeight!!.inv().toFloat(), mHeight!!.toFloat(),
            paint)
    }

    private fun testTranslate(paint: Paint) {
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 50f, paint)
    }
}