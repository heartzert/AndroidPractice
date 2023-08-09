package heartzert.lib.utils

import android.content.Intent
import java.net.URI

/**
 * Created by heartzert on 2020/8/22.
 * Email: heartzert@163.com
 */

/**
 * 获取Long型数据或null
 */
fun Intent.getLongExtraOrNull(key: String) = this.getLongExtra(key, 0).let { if (it == 0L) null else it }

/**
 * 获取Int型数据或null
 */
fun Intent.getIntExtraOrNull(key: String) = this.getIntExtra(key, 0).let { if (it == 0) null else it }

/**
 * 获取String型数据或null
 */
fun Intent.getStringExtraOrNull(key: String) = this.getStringExtra(key).let { if (it == "") null else it }


/**
 * 获取URI的参数的map
 */
fun URI.getParams(): Map<String, String>? {
    try {
        if (this.query.isNullOrEmpty()) return null
        val map = mutableMapOf<String, String>()
        for (x in this.query.split("&")) {
            val pare = x.split("=")
            map[pare[0]] = pare[1]
        }
        return map
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

/**
 * 获取URI的第一个path
 * 如：https://www.baidu.com/medic/hospital的medic
 */
fun URI.getFirstPath(): String {
    return try {
        if (this.path.startsWith("/"))
            this.path.substring(1, this.path.length).split("/")[0]
        else
            ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}