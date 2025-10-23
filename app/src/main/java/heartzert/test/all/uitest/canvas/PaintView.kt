package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by heartzert on 2025/10/23.
 * Email: heartzert@163.com
 *
 * 简单的画画功能View
 * 功能：
 * - 按下开始画线
 * - 移动时持续画线
 * - 抬起时结束并在终点画一个小圆点
 */
class PaintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "PaintView"
        private const val DOT_RADIUS_DP = 3f  // 终点圆点半径
    }

    // 画笔：用于画线条
    private val linePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = dp2px(1f)
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND  // 圆形线帽，让线条更平滑
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true  // 抗锯齿
    }

    // 画笔：用于画终点圆点
    private val dotPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // 当前正在绘制的路径
    private var currentPath: Path? = null

    // 已完成的路径列表（每条路径包含Path和终点位置）
    private val finishedPaths = mutableListOf<DrawingData>()

    // 绘制数据类：包含路径和终点位置
    private data class DrawingData(
        val path: Path,
        val endPoint: PointF
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制所有已完成的路径
        finishedPaths.forEach { drawingData ->
            canvas.drawPath(drawingData.path, linePaint)
            // 在终点画圆点
            canvas.drawCircle(
                drawingData.endPoint.x,
                drawingData.endPoint.y,
                dp2px(DOT_RADIUS_DP),
                dotPaint
            )
        }

        // 绘制当前正在画的路径
        currentPath?.let {
            canvas.drawPath(it, linePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 按下：开始新路径
                currentPath = Path().apply {
                    moveTo(x, y)
                }
                Log.d(TAG, "ACTION_DOWN: start drawing at ($x, $y)")
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                // 移动：继续画线
                currentPath?.lineTo(x, y)
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                // 抬起：完成路径，保存终点并画圆点
                currentPath?.let { path ->
                    finishedPaths.add(DrawingData(path, PointF(x, y)))
                    Log.d(TAG, "ACTION_UP: finish drawing at ($x, $y), total paths: ${finishedPaths.size}")
                }
                currentPath = null
                invalidate()
                return true
            }

            MotionEvent.ACTION_CANCEL -> {
                // 取消：放弃当前路径
                Log.d(TAG, "ACTION_CANCEL: discard current path")
                currentPath = null
                invalidate()
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 清空所有绘制内容
     */
    fun clear() {
        finishedPaths.clear()
        currentPath = null
        invalidate()
        Log.d(TAG, "clear: all paths cleared")
    }

    /**
     * dp转px
     */
    private fun dp2px(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}

