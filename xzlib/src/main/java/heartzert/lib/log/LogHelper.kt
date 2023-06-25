package heartzert.lib.log

import android.util.Log

/**
 * Created by heartzert on 2023/5/23.
 * Email: heartzert@163.com
 */
object CostLogHelper {

    var TAG = "=====wxz====="

    private val signMap = mutableMapOf<Int, Pair<Long, Long?>>()

    fun signTime(key: Int) {
        signMap[key] = Pair(System.currentTimeMillis(), null)
    }

    fun logdWithCost(key: Int, msg: String, showSpot: Boolean = false) {
        val time = signMap[key]?.first
        val currentTime = System.currentTimeMillis()
        val spotTime = signMap[key]?.second
        if (time != null) {
            val spotString = if (showSpot && spotTime != null) {
                ", last spot time cost: ${currentTime - time}"
            } else {
                ""
            }
            Log.d(TAG, "$msg, total time cost: ${currentTime - time}$spotString")
        } else {
            Log.d(TAG, "$msg, time cost: sign is null")
        }
        signMap[key] = Pair(currentTime, time)
    }

    fun logeWithCost(key: Int, msg: String, showSpot: Boolean = false) {
        val time = signMap[key]?.first
        val currentTime = System.currentTimeMillis()
        val spotTime = signMap[key]?.second
        if (time != null) {
            val spotString = if (showSpot && spotTime != null) {
                ", last spot time cost: ${currentTime - time}"
            } else {
                ""
            }
            Log.e(TAG, "$msg, total time cost: ${currentTime - time}$spotString")
        } else {
            Log.e(TAG, "$msg, time cost: sign is null")
        }
        signMap[key] = Pair(currentTime, time)
    }
}