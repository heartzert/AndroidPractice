package heartzert.lib.utils

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