package heartzert.test.all.custom_views.customview.views

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style.STROKE
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.util.Log
import android.view.View
import heartzert.lib.utils.dp2px

/**
 * Created by heartzert on 2019/10/8.
 * Email: heartzert@163.com
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
class DashView : View {

    private var mHeight = 0
    private var mWidth = 0
    private val RADIUS = dp2px(150f)
    private var ARC_WIDTH = dp2px(5f).toFloat()
    private var DASH_WIDTH = dp2px(2f).toFloat()

    private var paint = Paint()
    private var linePaint = Paint()
    private var arcPath = Path()
    private var recf =
        RectF(-RADIUS.toFloat(), -RADIUS.toFloat(), RADIUS.toFloat(), RADIUS.toFloat())
    private var dashRecf = RectF(-RADIUS.toFloat() + ARC_WIDTH / 2,
        -RADIUS.toFloat() + ARC_WIDTH / 2,
        RADIUS.toFloat() - ARC_WIDTH / 2,
        RADIUS.toFloat() - ARC_WIDTH / 2)
    private var pathEffect: PathDashPathEffect? = null
    private var dash = Path()
    private var dashPath = Path()
    private var shader = SweepGradient(0f, 0f, Color.parseColor("#FF00FF"),
        Color.parseColor("#FFFF00")).apply { setLocalMatrix(Matrix().apply { setRotate(90f) }) }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
        defStyleAttr)

    init {

        Log.d("========", "width = $width, height = $height")

        paint.apply {
            isAntiAlias = true
            style = STROKE
            strokeWidth = ARC_WIDTH
            strokeCap = Paint.Cap.BUTT
        }

        linePaint.apply {
            style = STROKE
        }

        arcPath.addArc(recf, 120f, 360f - 60f)
        dash.addRect(0f, 0f, DASH_WIDTH, dp2px(10f).toFloat(), Path.Direction.CW)
        dashPath.addArc(dashRecf, 120f, 300f)
        val measure = PathMeasure(dashPath, false)
        pathEffect = PathDashPathEffect(dash, ((measure.length - DASH_WIDTH)) / 20, 0f,
            PathDashPathEffect.Style.ROTATE)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mHeight = height
        mWidth = width
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(width.toFloat() / 2, height.toFloat() / 2)
        //画边框
        canvas.drawRect(-RADIUS - paint.strokeWidth / 2, -RADIUS - paint.strokeWidth / 2,
            +RADIUS + paint.strokeWidth / 2, +RADIUS + paint.strokeWidth / 2, linePaint)

        //画圆弧
        paint.shader = shader
        canvas.drawPath(arcPath, paint)
        paint.shader = null

        paint.pathEffect = pathEffect
        //画dash
        canvas.drawArc(dashRecf,
            120f,
            360f - 60f,
            false,
            paint
        )
        paint.pathEffect = null

        paint.style = Paint.Style.FILL
        canvas.drawCircle(0f, 0f, dp2px(5f).toFloat(), paint)

    }
}