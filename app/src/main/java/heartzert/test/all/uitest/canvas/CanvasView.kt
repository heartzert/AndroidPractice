package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withMatrix

/**
 * Created by heartzert on 2022/1/28.
 * Email: heartzert@163.com
 */
class CanvasView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    val pointPaint = Paint().apply {
        strokeWidth = 5f
    }

    val bluePointPaint =
        Paint().apply {
            strokeWidth = 5f
            color = Color.BLUE
            style = Paint.Style.STROKE
        }

    val redPointPaint =
        Paint().apply {
            strokeWidth = 5f
            color = Color.RED
            style = Paint.Style.STROKE
        }

    val yellowPointPaint =
        Paint().apply {
            strokeWidth = 5f
            color = Color.YELLOW
            style = Paint.Style.STROKE
        }

    val blackPointPaint =
        Paint().apply {
            strokeWidth = 5f
            color = Color.BLACK
            style = Paint.Style.STROKE
        }



    override fun onDraw(canvas: Canvas) {
        canvas.drawCoordinateLine()

        canvas.drawPoint(width / 2f, height / 2f, redPointPaint)
        canvas.drawRect(width / 2f - 100f, height / 2f - 100f, width / 2f + 100f, height / 2f + 100f, redPointPaint)

        val matrix1 = Matrix()
        val matrix2 = Matrix()
        val matrixconcat = Matrix()
        val matrixP = Matrix()

        val pMatrix1 = Matrix()
        val pMatrix2 = Matrix()

        val bigRec = RectF(width / 2f - 150f, height / 2f - 300f, width / 2f + 150f, height / 2f + 300f)
//        val hMid = (bigRec.right - bigRec.left) / 2f + bigRec.left
//        val vMid = (bigRec.bottom - bigRec.top) / 2f + bigRec.top
        matrix1.postRotate(45f, width / 2f, height / 2f)
        matrix1.postTranslate(100f, 100f)
        matrixP.postRotate(45f)
//        pMatrix1.preRotate(-45f, width / 2f, height / 2f)
//        pMatrix1.preTranslate(-100f, -100f)

        canvas.withMatrix(matrix1) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), yellowPointPaint)
            canvas.drawPoint(width / 2f, height / 2f, bluePointPaint)

            canvas.drawRect(bigRec, yellowPointPaint)
        }

        matrix2.postRotate(45f, width / 2f, height / 2f)
        matrixconcat.set(matrix1)
        matrixconcat.preConcat(matrix2)
        val point = PointF(width / 2f + 10f, height / 2f + 10f)
        val fl = floatArrayOf(10f, 10f)
        matrixP.mapPoints(fl)
//        matrixconcat.postTranslate(fl[0], fl[1])

//        pMatrix1.preRotate(-45f, width / 2f + 100f, height / 2f + 100f)
//        pMatrix1.preTranslate(-fl[0], -fl[1])
        canvas.withMatrix(matrixconcat) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), yellowPointPaint)
            canvas.drawPoint(point.x, point.y, blackPointPaint)

            val smallRec = RectF(width / 2f - 20f, height / 2f - 50f, width / 2f + 20f, height / 2f + 50f)
            canvas.drawRect(smallRec, bluePointPaint)
        }

//        val matrix1 = Matrix()
//        matrix1.postRotate(45f, width / 2f, height / 2f)
//        matrix1.postTranslate(100f, 100f)
//        val matrix2 = Matrix()
//        matrix2.postTranslate(-100f, -100f)
//        matrix2.postRotate(-45f, width / 2f, height / 2f)
//
//        canvas.drawLine(-10000f, 0f, 10000f, 0f, yellowPointPaint)
//        canvas.drawLine(0f, -10000f, 0f, 10000f, yellowPointPaint)
//
//        canvas.drawPoint(100f, 200f, redPointPaint)
//
//        canvas.drawLine(0f, height / 2f , width.toFloat(), height / 2f, redPointPaint)
//
//        canvas.drawPoint(150f, 200f, redPointPaint)
//        val fl = floatArrayOf(150f, 200f)
//        matrix1.mapPoints(fl)
//        canvas.drawPoint(fl[0], fl[1], bluePointPaint)
//
//        canvas.drawPoint(100f, 300f, redPointPaint.apply { strokeWidth = 10f })
//
//        canvas.withMatrix(matrix1) {
//            canvas.drawLine(-10000f, 0f, 10000f, 0f, blackPointPaint)
//            canvas.drawLine(0f, -10000f, 0f, 10000f, blackPointPaint)
//
//            canvas.drawPoint(100f, 200f, bluePointPaint)
//
//            canvas.drawLine(0f, height / 2f , width.toFloat(), height / 2f, bluePointPaint)
//
//
//            val fl2 = floatArrayOf(100f, 300f)
//            matrix2.mapPoints(fl2)
//            canvas.drawPoint(fl2[0], fl2[1], bluePointPaint)
//        }
    }
}