package heartzert.lib.utils

import kotlin.math.abs

/**
 * Created by heartzert on 2019/4/2.
 * Email: heartzert@163.com
 */

/**
 * 将参数转为负绝对值
 */
fun unAbs(int: Int) = -abs(int)
fun unAbs(long: Long) = -abs(long)
fun unAbs(float: Float) = -abs(float)
fun unAbs(double: Double) = -abs(double)
