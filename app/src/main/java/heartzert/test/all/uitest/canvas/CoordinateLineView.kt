package heartzert.test.all.uitest.canvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * Created by heartzert on 2024/09/29.
 * Email: heartzert@163.com
 */

private val coordinateLinePaint = Paint().apply {
    color = Color.RED
    strokeWidth = 4f
    textSize = 40f
}

private val tmpPaint = Paint(coordinateLinePaint)

/**
 * 绘制当前canvas的坐标系，每过100像素绘制一个短线在坐标轴上，并且在屏幕边缘处画一个箭头代表坐标正向
 */
fun Canvas.drawCoordinateLine(color: Int? = Color.RED, drawGrad: Boolean = false) {
    tmpPaint.set(coordinateLinePaint)

    if (color!= null) {
        tmpPaint.color = color
    }

    // 绘制 x 轴
    drawLine(0f, 0f, width.toFloat(), 0f, tmpPaint)

    // 绘制 y 轴
    drawLine(0f, 0f, 0f, height.toFloat(), tmpPaint)

    for (i in 0..width step 100) {
        // 绘制 x 轴上的短线
        drawLine(i.toFloat(), -5f, i.toFloat(), 5f, tmpPaint)
        // 绘制x轴网格线
        if (drawGrad) {
            tmpPaint.strokeWidth = 1f
            drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), tmpPaint)
            tmpPaint.strokeWidth = coordinateLinePaint.strokeWidth
        }
    }

    for (i in 0..height step 100) {
        // 绘制 y 轴上的短线
        drawLine(-5f, i.toFloat(), 5f, i.toFloat(), tmpPaint)
        // 绘制y轴网格线
        if (drawGrad) {
            tmpPaint.strokeWidth = 1f
            drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), tmpPaint)
            tmpPaint.strokeWidth = coordinateLinePaint.strokeWidth
        }
    }

    // 绘制 x 轴箭头
    drawLine(100f, 0f, 100f - 10, -10f, tmpPaint)
    drawLine(100f, 0f, 100f - 10, 10f, tmpPaint)
    drawText("x", 100f + 10, 10f, tmpPaint)

    // 绘制 y 轴箭头
    drawLine(0f, 100f, -10f, 100f - 10, tmpPaint)
    drawLine(0f, 100f, 10f, 100f - 10, tmpPaint)
    drawText("y", 10f, 100f + 10, tmpPaint)
}

