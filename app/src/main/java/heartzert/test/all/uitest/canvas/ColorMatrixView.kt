package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.View

/**
 * Created by heartzert on 2024/10/10.
 * Email: heartzert@163.com
 */
class ColorMatrixView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val bitmapCanvas = Canvas(bitmap)

    private val paint = Paint()

    init {
        bitmapCanvas.drawColor(Color.WHITE)
        bitmapCanvas.drawPath(Path().apply { addRect(10f, 10f, 20f, 50f, Path.Direction.CW) }, Paint().apply { color = Color.BLACK })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)


//        val colorMatrix = ColorMatrix()
//        colorMatrix.setSaturation(0f)
//        val targetColor = Color.parseColor("#99F771A0")
//        val r = Color.red(targetColor) / 255f
//        val g = Color.green(targetColor) / 255f
//        val b =Color.blue(targetColor) / 255f
//        val a = Color.alpha(targetColor) / 255f
//
////                val matrix = floatArrayOf(
////                    0f, 0f, 0f, 0f, r,
////                    0f, 0f, 0f, 0f, g,
////                    0f, 0f, 0f, 0f, b,
////                    0f, 0f, 0f, 1f, 0f
////                )
//        val matrix = floatArrayOf(
//            0f, 0f, 0f, 1f, 0f, // 红色
//            0f, 0f, 0f, 1f, 0f,  // 绿色
//            0f, 0f, 0f, 1f, 0f,        // 蓝色
//            0f, 0f, 0f, 0f, 0f                                      // Alpha
//        )
//        colorMatrix.set(matrix)
//        val paint = Paint()
//        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)

//            // 创建一个相同尺寸的 Bitmap 作为输出
//            val resultBitmap = Bitmap.createBitmap(bitmap)
//            val canvas2 = Canvas(resultBitmap)
//            val paint = Paint()
//
//            // 使用 PorterDuff.Mode.DST_IN 来保留非黑色的像素
//            val transparentColor = Color.TRANSPARENT
//            val blackColor = Color.BLACK
//
//            // 创建 ColorFilter，将黑色替换为透明
//            val colorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.DST_IN)
//            paint.colorFilter = colorFilter
//
//            // 在 Canvas 上绘制位图，将黑色像素变为透明
//            canvas2.drawBitmap(bitmap, 0f, 0f, paint)




        canvas.drawBitmap(bitmap, 200f, 200f, paint)
    }
}
