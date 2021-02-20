package heartzert.lib.utils

import kotlin.math.abs
import kotlin.math.pow

/**
 * Created by heartzert on 2019/4/2.
 * Email: heartzert@163.com
 */

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


