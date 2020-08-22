package heartzert.lib.utils.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ImageSpan

/**
 * Created by heartzert on 2019/11/22.
 * Email: heartzert@163.com
 *
 * 在文字前添加图片的ImageSpan，并使图片对于文字居中
 */
class MyImageSpan(context: Context, resourceId: Int) : ImageSpan(context, resourceId) {

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int {
        val rect = drawable.bounds
        if (fm != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.bottom - fmPaint.top
            val drHeight = rect.bottom - rect.top

            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4

            fm.ascent = -bottom
            fm.top = -bottom
            fm.bottom = top
            fm.descent = top
        }
        return rect.right
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int,
                      x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        //根据UI要求，居中后向下偏移4个像素
        val offset = 4
        canvas.save()
        val transY = (bottom - top - drawable.bounds.bottom + offset) / 2f + top
        canvas.translate(x, transY)
        drawable.draw(canvas)
        canvas.restore()
    }
}