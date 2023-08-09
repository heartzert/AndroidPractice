package heartzert.lib.utils.tools

import android.graphics.Color

/**
 * Created by heartzert on 2019/6/13.
 * Email: heartzert@163.com
 */

/**
 * 将rgba(256,256,256,0.5)形式的string转换为#FFFFFFFF形式
 *
 * 若值大于256则自动舍去大于256的部分
 *
 * @param rgbString
 * @param withTransparent 是否返回包含透明度的颜色，默认返回
 * @return Color的Int值
 */
fun transRGBAToString(rgbString: String?, withTransparent: Boolean = true): Int? {
    if (rgbString.isNullOrBlank()) return null

    try {
        if (!rgbString.trim().startsWith("rgba")) return null
        val color = rgbString.substring(rgbString.indexOf("(") + 1, rgbString.indexOf(")"))
        val list = color.split(",")
        if (list.size < 4) return null
        val format = "%2S"
        val r = format.format(list[0].trim().toInt().toString(16)).replace(" ", "0")
            .run { substring(length - 2, length) }
        val g = format.format(list[1].trim().toInt().toString(16)).replace(" ", "0")
            .run { substring(length - 2, length) }
        val b = format.format(list[2].trim().toInt().toString(16)).replace(" ", "0")
            .run { substring(length - 2, length) }
        val a = format.format((list[3].trim().toDouble() * 255).toInt().toString(16)).replace(" ", "0")
            .run { substring(length - 2, length) }
        return if (withTransparent) {
            Color.parseColor("#$a$r$g$b")
        } else {
            Color.parseColor("#$r$g$b")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}