package heartzert.lib.utils

/**
 * Created by heartzert on 2020/5/12.
 * Email: heartzert@163.com
 */
fun isNotNull(vararg a : Any?, callBack: () -> Unit) {
    for (x in a) {
        x ?: return
    }
    callBack.invoke()
}