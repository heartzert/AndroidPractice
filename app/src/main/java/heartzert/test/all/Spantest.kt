package heartzert.test.all

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode.CLAMP
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * Created by heartzert on 2024/1/23.
 * Email: heartzert@163.com
 */
class LinearGradientForegroundSpan(private val colorList: IntArray, private val positions: FloatArray?, private val textLength: Int) : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(textPaint: TextPaint?) {
        if (textPaint != null)
            textPaint.shader = LinearGradient(0f, 0f, textPaint.textSize * textLength, 0f, colorList, positions, CLAMP)
    }
}

class LinearGradientForegroundSpan1(private val startLength: Int, private val colorList: IntArray, private val positions: FloatArray?, private val textLength: Int) : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(textPaint: TextPaint?) {
        if (textPaint != null)
            textPaint.shader = LinearGradient(0f, 0f, textPaint.textSize * textLength, 0f, colorList, positions, Shader.TileMode.CLAMP)
    }
}