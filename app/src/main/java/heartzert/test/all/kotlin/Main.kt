package heartzert.test.all.kotlin

import heartzert.lib.utils.jsonToMap
import org.json.JSONObject

/**
 * Created by heartzert on 2020/5/19.
 * Email: heartzert@163.com
 */

fun main() {
    println("0.3".toDoubleOrNull() ?: 0.0 - 10.0 < 0.00001)
}