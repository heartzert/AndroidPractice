package heartzert.lib.utils

import android.net.Uri
import java.net.URI
import java.util.Locale

/**
 * Created by heartzert on 2019/6/12.
 * Email: heartzert@163.com
 */

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

fun URI.isGif(): Boolean {
    return this.path?.toLowerCase(Locale.getDefault())?.endsWith(".gif") == true
}