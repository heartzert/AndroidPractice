package heartzert.test.algrithom.kotlin

import java.math.BigDecimal

/**
 * Created by heartzert on 2020/5/19.
 * Email: heartzert@163.com
 */

fun main() {
    val list = arrayOf("123.4","123.45", null)
    var total = BigDecimal.ZERO
    for (x in list) {
        total = total.add(x?.toBigDecimalOrNull() ?: BigDecimal.ZERO).multiply(1.toBigDecimal())
    }
    println(total)
}