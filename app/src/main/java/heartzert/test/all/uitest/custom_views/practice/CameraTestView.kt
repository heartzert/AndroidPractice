package heartzert.test.all.uitest.custom_views.practice

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import heartzert.lib.utils.ImageUtil
import heartzert.test.all.R

/**
 * Created by heartzert on 2019/9/30.
 * Email: heartzert@163.com
 */
class CameraTestView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
        defStyleAttr)

    private val camera = Camera()
    private val nolmalCamera = Camera()
    private var imgWidth = 300f
    private var imgHeight = 200f

    private val paint = Paint()

    init {
        camera.rotateX(30f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate((100f + imgWidth / 2), (100f + imgHeight / 2))
        camera.applyToCanvas(canvas)
        canvas.translate(- (100f + imgWidth / 2), -(100f + imgHeight / 2))
        canvas.drawBitmap(ImageUtil.getImage(resources, R.drawable.iamge_square_yellow, imgWidth.toInt(), imgHeight.toInt()), 100f, 100f, paint)
//        nolmalCamera.applyToCanvas(canvas)
//        canvas.drawBitmap(ImageUtil.getImage(resources, R.drawable.iamge_square_yellow, imgWidth.toInt(), imgHeight.toInt()), 100f, 100f, paint)
    }

    fun test() {

    }
}
