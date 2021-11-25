package heartzert.test.all.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by heartzert on 8/31/21.
 * Email: heartzert@163.com
 */

private val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

/**
 * 打印现在时间以及线程名字
 */
fun log(msg: Any?) = println("${now()} [${Thread.currentThread().name}] $msg")