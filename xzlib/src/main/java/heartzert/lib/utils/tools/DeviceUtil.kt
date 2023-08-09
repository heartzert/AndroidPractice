package heartzert.lib.utils.tools

import android.content.Context
import android.os.Build
import android.provider.Settings

/**
 * Created by heartzert on 2020/11/6.
 * Email: heartzert@163.com
 */
object DeviceUtil {

    fun isOppo(): Boolean {
        return "oppo".equals(Build.MANUFACTURER, ignoreCase = true)
                || Build.FINGERPRINT.contains("oppo")
    }

    fun isVivo(): Boolean {
        return "vivo".equals(Build.MANUFACTURER, ignoreCase = true)
                || Build.FINGERPRINT.contains("vivo")
    }

    fun isHuawei(): Boolean {
        return "huawei".equals(Build.MANUFACTURER, ignoreCase = true)
                || Build.FINGERPRINT.contains("huawei")
    }

    fun isSanxing(): Boolean {
        return "sanxing".equals(Build.MANUFACTURER, ignoreCase = true)
                || "Samsung".equals(Build.MANUFACTURER, ignoreCase = true)
                || Build.FINGERPRINT.contains("sanxing")
                || Build.FINGERPRINT.contains("Samsung")
    }

    fun isXiaomi(): Boolean {
        return Build.MANUFACTURER == "Xiaomi"
    }

    fun isXiaomiAllPanel(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver, "force_fsg_nav_bar", 0) != 0
    }
}