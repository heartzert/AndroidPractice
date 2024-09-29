package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class BezierGradientCurveView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
        private val path = Path()
        private val colors = intArrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE)
        private val positions = floatArrayOf(0f, 0.33f, 0.66f, 1f)
        private lateinit var gradient: LinearGradient
        private val pathMeasure = PathMeasure()
        private val matrix = Matrix()

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)

            path.reset()
            path.moveTo(0f, height / 2f)
            path.cubicTo(width / 4f, 0f, width * 3 / 4f, height.toFloat(), width.toFloat(), height / 2f)

            pathMeasure.setPath(path, false)
            val pathLength = pathMeasure.length
            gradient = LinearGradient(0f, 0f, pathLength, 0f, colors, positions, Shader.TileMode.CLAMP)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            var distance = 0f
            val length = pathMeasure.length
            while (distance < length) {
                matrix.reset()
                pathMeasure.getMatrix(distance, matrix, PathMeasure.TANGENT_MATRIX_FLAG)
                paint.shader = gradient.apply { setLocalMatrix(matrix) }
                canvas.drawPath(path, paint)
                distance += 1f // 调整步长以控制渐变的精细程度
            }
        }
    }