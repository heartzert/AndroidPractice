package heartzert.test.all.custom_views.draw_shadow.shadow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2019/9/24.
 * Email: heartzert@163.com
 */
class ShadowView : View {

    val paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
        defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.apply {
            color = Color.parseColor("#FFFFFFFF")
        }

        paint.setShadowLayer(10f,10f,10f, Color.parseColor("#3DFF7070"))
        canvas.drawRoundRect(RectF(0f,0f,200f,200f),10f,10f,paint)


    }
}