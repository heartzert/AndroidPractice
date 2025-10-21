package com.heartzert.autoswipe

import android.content.Context
import android.content.SharedPreferences

/**
 * 滑动配置数据类
 */
data class SwipeConfig(
    // 滑动间隔最小值（秒）
    val intervalMin: Float = 1f,
    // 滑动间隔最大值（秒）
    val intervalMax: Float = 3f,
    // 起始位置随机范围（dp）
    val startPositionRange: Float = 100f,
    // 滑动距离最小值（dp）
    val swipeDistanceMin: Float = 15f,
    // 滑动距离最大值（dp）
    val swipeDistanceMax: Float = 40f,
    // 滑动时长最小值（秒）
    val swipeDurationMin: Float = 0.6f,
    // 滑动时长最大值（秒）
    val swipeDurationMax: Float = 0.8f
) {
    /**
     * 获取随机滑动间隔（毫秒）
     */
    fun getRandomInterval(): Long {
        val randomSeconds = intervalMin + (intervalMax - intervalMin) * Math.random().toFloat()
        return (randomSeconds * 1000).toLong()
    }

    /**
     * 获取随机滑动时长（毫秒）
     */
    fun getRandomDuration(): Long {
        val randomSeconds = swipeDurationMin + (swipeDurationMax - swipeDurationMin) * Math.random().toFloat()
        return (randomSeconds * 1000).toLong()
    }

    /**
     * 验证配置是否有效
     */
    fun isValid(): Boolean {
        return intervalMin > 0 && intervalMax >= intervalMin &&
                startPositionRange > 0 &&
                swipeDistanceMin > 0 && swipeDistanceMax >= swipeDistanceMin &&
                swipeDurationMin > 0 && swipeDurationMax >= swipeDurationMin
    }
}

/**
 * 配置管理类
 */
object SwipeConfigManager {
    private const val PREFS_NAME = "swipe_config"
    private const val KEY_INTERVAL_MIN = "interval_min"
    private const val KEY_INTERVAL_MAX = "interval_max"
    private const val KEY_START_POSITION_RANGE = "start_position_range"
    private const val KEY_SWIPE_DISTANCE_MIN = "swipe_distance_min"
    private const val KEY_SWIPE_DISTANCE_MAX = "swipe_distance_max"
    private const val KEY_SWIPE_DURATION_MIN = "swipe_duration_min"
    private const val KEY_SWIPE_DURATION_MAX = "swipe_duration_max"
    private const val KEY_IS_ENABLED = "is_enabled"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存配置
     */
    fun saveConfig(context: Context, config: SwipeConfig) {
        getPrefs(context).edit().apply {
            putFloat(KEY_INTERVAL_MIN, config.intervalMin)
            putFloat(KEY_INTERVAL_MAX, config.intervalMax)
            putFloat(KEY_START_POSITION_RANGE, config.startPositionRange)
            putFloat(KEY_SWIPE_DISTANCE_MIN, config.swipeDistanceMin)
            putFloat(KEY_SWIPE_DISTANCE_MAX, config.swipeDistanceMax)
            putFloat(KEY_SWIPE_DURATION_MIN, config.swipeDurationMin)
            putFloat(KEY_SWIPE_DURATION_MAX, config.swipeDurationMax)
            apply()
        }
    }

    /**
     * 加载配置
     */
    fun loadConfig(context: Context): SwipeConfig {
        val prefs = getPrefs(context)
        return SwipeConfig(
            intervalMin = prefs.getFloat(KEY_INTERVAL_MIN, 1f),
            intervalMax = prefs.getFloat(KEY_INTERVAL_MAX, 3f),
            startPositionRange = prefs.getFloat(KEY_START_POSITION_RANGE, 100f),
            swipeDistanceMin = prefs.getFloat(KEY_SWIPE_DISTANCE_MIN, 15f),
            swipeDistanceMax = prefs.getFloat(KEY_SWIPE_DISTANCE_MAX, 40f),
            swipeDurationMin = prefs.getFloat(KEY_SWIPE_DURATION_MIN, 0.6f),
            swipeDurationMax = prefs.getFloat(KEY_SWIPE_DURATION_MAX, 0.8f)
        )
    }

    /**
     * 保存启用状态
     */
    fun setEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_IS_ENABLED, enabled).apply()
    }

    /**
     * 获取启用状态
     */
    fun isEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_ENABLED, false)
    }
}

