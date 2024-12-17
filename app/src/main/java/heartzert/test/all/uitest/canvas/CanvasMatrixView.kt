package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withMatrix

/**
 * Created by heartzert on 2024/09/29.
 * Email: heartzert@163.com
 *
 * 测试canvas使用matrix位移以及rect使用matrix位移后的效果
 *
 * 结论：matrix移动后，canvas的坐标系也会跟着移动，即移动100，100后，坐标原点也变到100，100的位置了。
 * 但是matrix的坐标设置时，一直是以view的左上角为0，0点的
 *
 */
class CanvasMatrixView@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val tmpMatrix = Matrix().apply {
        postTranslate(300f, 400f)
        postScale(1.2f, 1.2f)
        postRotate(45f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCoordinateLine()
        canvas.withMatrix(tmpMatrix) {
            canvas.drawCoordinateLine(Color.GREEN)
        }
    }
}
