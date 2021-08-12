package heartzert.lib.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.RoundingMode.HALF_EVEN
import java.util.regex.Pattern

/**
 * Created by heartzert on 2019/6/13.
 * Email: heartzert@163.com
 */

/**
 * string是否为纯数字
 */
fun String.isPureNumber(): Boolean {
    for (x in toCharArray()) {
        if (!x.isDigit()) return false
    }
    return true
}

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