package heartzert.test.all.uitest

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.withClip

/**
 * Created by heartzert on 5/24/21.
 * Email: heartzert@163.com
 */
class ClipView : View {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK }
    val paint2 = Paint().apply { color = Color.BLUE }
    val transparentPaint = Paint().apply { color = Color.TRANSPARENT }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        testDraw2(canvas)
    }

    private fun testDraw(canvas: Canvas) {
        //先画一个圆角矩形,也就是透明区域(Destination image)
        val path = Path()
        path.addRoundRect(100f, 100f, 300f, 400f,40f,40f, Path.Direction.CW)
        canvas.save()
        canvas.clipPath(path)
        canvas.drawRoundRect(100f, 100f, 300f, 400f, 30f, 30f, paint)
        canvas.restore()
        paint.color = Color.BLUE
        canvas.drawCircle(300f, 400f, 100f, paint)
//        //设置遮罩的颜色
//        paint.color = Color.BLUE
//        //设置paint的 xfermode 为PorterDuff.Mode.SRC_OUT
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        //画遮罩的矩形(Source image)
//        canvas.drawRect(10f, 10f, 200f, 200f, paint)
//        canvas.drawRect(200f, 200f, 400f, 400f, paint)
//        //清空paint 的 xfermode
//        paint.xfermode = null
//        canvas?.drawRect(0f, 0f, 300f, 300f, paint1)
//        canvas?.drawRoundRect(0f, 0f, 300f, 300f, 5f, 5f, paint2)
    }

    private fun testDraw2(canvas: Canvas) {
    }
}