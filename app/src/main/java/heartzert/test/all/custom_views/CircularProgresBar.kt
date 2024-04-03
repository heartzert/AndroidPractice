package heartzert.test.all.custom_views

/**
 * Created by heartzert on 2023/12/4.
 * Email: heartzert@163.com
 */
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import heartzert.lib.utils.dp

class CircularProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val fgStartColor = Color.parseColor("#FF0FB7FF")
    private val fgEndColor = Color.parseColor("#FF257CFF")

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#CC000000")
        style = Paint.Style.STROKE
        strokeWidth = 8.dp.toFloat()
    }
    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8.dp.toFloat()
        strokeCap = Paint.Cap.ROUND
    }
    private val oval: RectF = RectF()
    private var gradient: SweepGradient? = null

    private var progress = 0 // 进度，取值范围 0 到 100

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 计算圆圈的外接矩形
        val padding = 9.dp.toFloat()
        oval.set(padding, padding, width - padding, height - padding)

        // 绘制灰色背景圆圈
        canvas.drawOval(oval, backgroundPaint)

        // 绘制进度圆弧
        val angle = -360 * progress / 100 // 计算进度对应的角度
        progressPaint.shader = gradient
        canvas.drawArc(oval, -90f, angle.toFloat(), false, progressPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gradient = SweepGradient(
            width / 2f, height / 2f,
            intArrayOf(fgStartColor, fgEndColor, fgStartColor),
            floatArrayOf(0f, 0.5f, 1f)
        ).apply {
            val matrix = Matrix()
            matrix.setRotate(-90f, width / 2f, height / 2f)
            setLocalMatrix(matrix)
        }
    }

    fun getProgress(): Int {
        return progress
    }

    fun setProgress(progress: Int) {
        if (progress in 0..100) {
            this.progress = progress
            invalidate() // 重新绘制
        }
    }
}

