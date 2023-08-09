package heartzert.lib.utils

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.RoundingMode.HALF_EVEN
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.pow

/**
 * Created by heartzert on 2020/5/12.
 * Email: heartzert@163.com
 */

/**
 * 若参数a全不为空则执行callback
 */
fun isAllNotNull(vararg a : Any?, callBack: () -> Unit) {
    for (x in a) {
        x ?: return
    }
    callBack.invoke()
}

/**
 * 将参数转为负绝对值
 */
fun Int.unAbs() = -abs(this)
fun Long.unAbs() = -abs(this)
fun Float.unAbs() = -abs(this)
fun Double.unAbs() = -abs(this)

/**
 * 平方
 */
fun Int.square() = toDouble().pow(2)
fun Long.square() = toDouble().pow(2)
fun Float.square() = toDouble().pow(2)
fun Double.square() = toDouble().pow(2)

/**
 * 开平方
 */
fun Int.squareRoot() = toDouble().pow(0.5)
fun Long.squareRoot() = toDouble().pow(0.5)
fun Float.squareRoot() = toDouble().pow(0.5)
fun Double.squareRoot() = toDouble().pow(0.5)

/**
 * 把纯数字转换为万为单位,保留digital位小数,小于一万不处理
 */
fun String.transformToW(digital: Int): String {
    if (digital >= 0) {
        val num = this.toBigDecimalOrNull() ?: return ""
        if (num < 10000.toBigDecimal()) return this
        val result = num.divide(10000.toBigDecimal())?.setScale(digital, HALF_EVEN)?.toString()
        return if (result.isNullOrEmpty()) "" else result + "万"
    }
    return ""
}

/**
 *  json 转化成 Map
 */
fun String.jsonToMap(): Map<String, Any>? {
    val map = Gson().fromJson<Map<String, String>>(
        this,
        object : TypeToken<Map<String, String>>() {}.type
    )
    return map?.toSortedMap()
}

/**
 * 去除数字前的国家符号，只保留数字和小数点
 */
fun String.removePriceSymbol(): String? {
    if (this.isEmpty()) {
        return null
    }
    val REGEX = "[^(0-9).]"
    return Pattern.compile(REGEX).matcher(this).replaceAll("").trim()
}

/**
 * 是否是View的子类
 */
fun String.isSubClassOfView(): Boolean {
    return View::class.java.isAssignableFrom(Class.forName(this))
}