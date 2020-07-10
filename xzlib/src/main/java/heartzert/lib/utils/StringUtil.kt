package heartzert.lib.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.RoundingMode.HALF_EVEN

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
fun transformToW(string: String?, digital: Int): String {
    if (digital >= 0) {
        val num = string?.toBigDecimalOrNull() ?: return ""
        if (num < 10000.toBigDecimal()) return string
        val result = num.divide(10000.toBigDecimal())?.setScale(digital, HALF_EVEN)?.toString()
        return if (result.isNullOrEmpty()) "" else result + "万"
    }
    return ""
}

/**
 *  json 转化成 Map
 */
fun jsonToMap(jsonStr: String): Map<String, Any>? {
    val map = Gson().fromJson<Map<String, String>>(jsonStr, object : TypeToken<Map<String, String>>() {}.type)
    return map?.toSortedMap()
}