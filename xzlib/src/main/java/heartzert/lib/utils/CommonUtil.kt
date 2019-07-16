package heartzert.lib.utils

import kotlin.math.abs

/**
 * Created by heartzert on 2019/4/2.
 * Email: heartzert@163.com
 */

/**
 * 将参数转为负绝对值
 */
fun unAbs(int: Int) = 0 - abs(int)
fun unAbs(float: Float) = 0f - abs(float)
fun unAbs(double: Double) = 0.0 - abs(double)
fun unAbs(long: Long) = 0 - abs(long)
