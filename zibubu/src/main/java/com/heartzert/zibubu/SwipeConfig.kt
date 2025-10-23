package com.heartzert.zibubu

import android.content.Context
import android.content.SharedPreferences
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * 插值器类型枚举
 */
enum class InterpolatorType(val interpolator: Interpolator, val description: String) {
    LINEAR(LinearInterpolator(), "匀速"),
    ACCELERATE(AccelerateInterpolator(), "加速"),
    DECELERATE(DecelerateInterpolator(), "减速"),
    ACCELERATE_DECELERATE(AccelerateDecelerateInterpolator(), "先加速后减速");
    
    companion object {
        /**
         * 获取所有插值器类型
         */
        fun getAllTypes(): List<InterpolatorType> = values().toList()
        
        /**
         * 随机选择一个插值器类型
         */
        fun random(): InterpolatorType {
            val types = getAllTypes()
            val index = (Math.random() * types.size).toInt()
            return types[index]
        }
    }
}

/**
 * 随机间隔信息
 */
data class IntervalInfo(
    val tier: Int,              // 档位：1、2、3
    val baseSeconds: Float,     // 基础秒数（第二层随机结果）
    val multiplier: Float,      // 档位倍数
    val finalMillis: Long       // 最终间隔（毫秒）
)

/**
 * 滑动配置数据类
 */
data class SwipeConfig(
    // 基础配置
    // 滑动间隔最小值（秒）
    val intervalMin: Float = DEFAULT_INTERVAL_MIN,
    // 滑动间隔最大值（秒）
    val intervalMax: Float = DEFAULT_INTERVAL_MAX,
    // 起始位置随机范围（dp）
    val startPositionRange: Float = DEFAULT_START_POSITION_RANGE,
    // 滑动距离最小值（dp）
    val swipeDistanceMin: Float = DEFAULT_SWIPE_DISTANCE_MIN,
    // 滑动距离最大值（dp）
    val swipeDistanceMax: Float = DEFAULT_SWIPE_DISTANCE_MAX,
    // 滑动时长最小值（秒）
    val swipeDurationMin: Float = DEFAULT_SWIPE_DURATION_MIN,
    // 滑动时长最大值（秒）
    val swipeDurationMax: Float = DEFAULT_SWIPE_DURATION_MAX,
    
    // 高级配置
    // 最大倾角（度）
    val maxSwipeAngle: Float = DEFAULT_MAX_SWIPE_ANGLE,
    // 抖动强度最小值（像素）
    val jitterIntensityMin: Float = DEFAULT_JITTER_INTENSITY_MIN,
    // 抖动强度最大值（像素）
    val jitterIntensityMax: Float = DEFAULT_JITTER_INTENSITY_MAX,
    // 曲度强度最小值（像素）
    val curveIntensityMin: Float = DEFAULT_CURVE_INTENSITY_MIN,
    // 曲度强度最大值（像素）
    val curveIntensityMax: Float = DEFAULT_CURVE_INTENSITY_MAX,
    // 回勾概率（0-100）
    val backhookProbability: Int = DEFAULT_BACKHOOK_PROBABILITY,
    // 回勾长度比例（0-1）
    val backhookLengthRatio: Float = DEFAULT_BACKHOOK_LENGTH_RATIO,
    
    // 档位概率配置
    // 档位1概率（%）
    val tier1Probability: Float = DEFAULT_TIER_1_PROBABILITY,
    // 档位2概率（%）
    val tier2Probability: Float = DEFAULT_TIER_2_PROBABILITY,
    // 档位3概率（%）
    val tier3Probability: Float = DEFAULT_TIER_3_PROBABILITY
) {
    /**
     * 获取随机滑动间隔（毫秒）
     * 使用两层随机：
     * 第一层：随机选择档位（1档60%、2档30%、3档10%）
     * 第二层：根据档位计算间隔（2档是1档的2倍，3档是2档的2倍）
     */
    fun getRandomInterval(): Long {
        // 第一层随机：选择档位
        val tier = selectRandomTier()
        
        // 第二层随机：在档位对应的范围内随机
        val baseRandomSeconds = intervalMin + (intervalMax - intervalMin) * Math.random().toFloat()
        
        // 根据档位应用倍数
        val finalSeconds = when (tier) {
            1 -> baseRandomSeconds                    // 1档：基础间隔
            2 -> baseRandomSeconds * TIER_2_MULTIPLIER  // 2档：2倍间隔
            3 -> baseRandomSeconds * TIER_3_MULTIPLIER  // 3档：4倍间隔
            else -> baseRandomSeconds
        }
        
        return (finalSeconds * 1000).toLong()
    }
    
    /**
     * 获取随机滑动间隔（带详细信息）
     * 返回档位、基础时间、倍数等信息，用于日志输出
     */
    fun getRandomIntervalWithInfo(): IntervalInfo {
        // 第一层随机：选择档位
        val tier = selectRandomTier()
        
        // 第二层随机：在档位对应的范围内随机
        val baseRandomSeconds = intervalMin + (intervalMax - intervalMin) * Math.random().toFloat()
        
        // 根据档位应用倍数
        val multiplier = when (tier) {
            1 -> 1f
            2 -> TIER_2_MULTIPLIER
            3 -> TIER_3_MULTIPLIER
            else -> 1f
        }
        
        val finalSeconds = baseRandomSeconds * multiplier
        val finalMillis = (finalSeconds * 1000).toLong()
        
        return IntervalInfo(tier, baseRandomSeconds, multiplier, finalMillis)
    }
    
    /**
     * 随机选择档位
     * 1档：60%概率
     * 2档：30%概率
     * 3档：10%概率
     */
    private fun selectRandomTier(): Int {
        val random = Math.random() * 100  // 0-100的随机数
        return when {
            random < tier1Probability -> 1      // 档位1
            random < tier1Probability + tier2Probability -> 2  // 档位2
            else -> 3                              // 档位3
        }
    }

    /**
     * 获取随机滑动时长（毫秒）
     */
    fun getRandomDuration(): Long {
        val randomSeconds = swipeDurationMin + (swipeDurationMax - swipeDurationMin) * Math.random().toFloat()
        return (randomSeconds * 1000).toLong()
    }
    
    /**
     * 获取随机倾角（角度）
     * 正值：向右倾斜
     * 负值：向左倾斜
     */
    fun getRandomAngle(): Float {
        // 在 -maxSwipeAngle 到 +maxSwipeAngle 之间随机
        return (Math.random().toFloat() - 0.5f) * 2 * maxSwipeAngle
    }
    
    /**
     * 获取随机插值器
     * 返回随机选择的插值器类型
     */
    fun getRandomInterpolatorType(): InterpolatorType {
        return InterpolatorType.random()
    }

    /**
     * 验证配置是否有效
     */
    fun isValid(): Boolean {
        return intervalMin > 0 && intervalMax >= intervalMin &&
                startPositionRange > 0 &&
                swipeDistanceMin > 0 && swipeDistanceMax >= swipeDistanceMin &&
                swipeDurationMin > 0 && swipeDurationMax >= swipeDurationMin &&
                maxSwipeAngle >= 0 &&
                jitterIntensityMin >= 0 && jitterIntensityMax >= jitterIntensityMin &&
                curveIntensityMin >= 0 && curveIntensityMax >= curveIntensityMin &&
                backhookProbability in 0..100 &&
                backhookLengthRatio >= 0 && backhookLengthRatio <= 1 &&
                tier1Probability >= 0 && tier2Probability >= 0 && tier3Probability >= 0 &&
                kotlin.math.abs(tier1Probability + tier2Probability + tier3Probability - 100f) < 0.01f  // 三个概率之和应该约等于100
    }

    companion object {
        // 基础配置默认值
        const val DEFAULT_INTERVAL_MIN = 1f
        const val DEFAULT_INTERVAL_MAX = 5f
        const val DEFAULT_START_POSITION_RANGE = 250f
        const val DEFAULT_SWIPE_DISTANCE_MIN = 50f
        const val DEFAULT_SWIPE_DISTANCE_MAX = 150f
        const val DEFAULT_SWIPE_DURATION_MIN = 0.5f
        const val DEFAULT_SWIPE_DURATION_MAX = 2f
        
        // 高级配置默认值
        const val DEFAULT_MAX_SWIPE_ANGLE = 25f              // 最大倾角：±25度
        const val DEFAULT_JITTER_INTENSITY_MIN = 1f          // 最小抖动强度（像素）
        const val DEFAULT_JITTER_INTENSITY_MAX = 4f          // 最大抖动强度（像素）
        const val DEFAULT_CURVE_INTENSITY_MIN = 5f           // 最小曲度强度（像素）
        const val DEFAULT_CURVE_INTENSITY_MAX = 20f          // 最大曲度强度（像素）
        const val DEFAULT_BACKHOOK_PROBABILITY = 30          // 回勾概率：30%
        const val DEFAULT_BACKHOOK_LENGTH_RATIO = 0.1f       // 回勾长度比例：10%
        
        // 档位概率配置默认值（百分比）
        const val DEFAULT_TIER_1_PROBABILITY = 60f  // 1档概率：60%
        const val DEFAULT_TIER_2_PROBABILITY = 35f  // 2档概率：35%
        const val DEFAULT_TIER_3_PROBABILITY = 5f   // 3档概率：5%
        
        // 档位间隔倍数 - 固定不可配置
        const val TIER_2_MULTIPLIER = 2f     // 2档是1档的2倍
        const val TIER_3_MULTIPLIER = 4f     // 3档是1档的4倍（2档的2倍）
    }
}

/**
 * 配置管理类
 */
object SwipeConfigManager {
    private const val PREFS_NAME = "zibubu_config"
    // 基础配置Key
    private const val KEY_INTERVAL_MIN = "interval_min"
    private const val KEY_INTERVAL_MAX = "interval_max"
    private const val KEY_START_POSITION_RANGE = "start_position_range"
    private const val KEY_SWIPE_DISTANCE_MIN = "swipe_distance_min"
    private const val KEY_SWIPE_DISTANCE_MAX = "swipe_distance_max"
    private const val KEY_SWIPE_DURATION_MIN = "swipe_duration_min"
    private const val KEY_SWIPE_DURATION_MAX = "swipe_duration_max"
    // 高级配置Key
    private const val KEY_MAX_SWIPE_ANGLE = "max_swipe_angle"
    private const val KEY_JITTER_INTENSITY_MIN = "jitter_intensity_min"
    private const val KEY_JITTER_INTENSITY_MAX = "jitter_intensity_max"
    private const val KEY_CURVE_INTENSITY_MIN = "curve_intensity_min"
    private const val KEY_CURVE_INTENSITY_MAX = "curve_intensity_max"
    private const val KEY_BACKHOOK_PROBABILITY = "backhook_probability"
    private const val KEY_BACKHOOK_LENGTH_RATIO = "backhook_length_ratio"
    // 档位概率Key
    private const val KEY_TIER_1_PROBABILITY = "tier_1_probability"
    private const val KEY_TIER_2_PROBABILITY = "tier_2_probability"
    private const val KEY_TIER_3_PROBABILITY = "tier_3_probability"
    // 状态Key
    private const val KEY_IS_ENABLED = "is_enabled"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存配置
     */
    fun saveConfig(context: Context, config: SwipeConfig) {
        getPrefs(context).edit().apply {
            // 基础配置
            putFloat(KEY_INTERVAL_MIN, config.intervalMin)
            putFloat(KEY_INTERVAL_MAX, config.intervalMax)
            putFloat(KEY_START_POSITION_RANGE, config.startPositionRange)
            putFloat(KEY_SWIPE_DISTANCE_MIN, config.swipeDistanceMin)
            putFloat(KEY_SWIPE_DISTANCE_MAX, config.swipeDistanceMax)
            putFloat(KEY_SWIPE_DURATION_MIN, config.swipeDurationMin)
            putFloat(KEY_SWIPE_DURATION_MAX, config.swipeDurationMax)
            // 高级配置
            putFloat(KEY_MAX_SWIPE_ANGLE, config.maxSwipeAngle)
            putFloat(KEY_JITTER_INTENSITY_MIN, config.jitterIntensityMin)
            putFloat(KEY_JITTER_INTENSITY_MAX, config.jitterIntensityMax)
            putFloat(KEY_CURVE_INTENSITY_MIN, config.curveIntensityMin)
            putFloat(KEY_CURVE_INTENSITY_MAX, config.curveIntensityMax)
            putInt(KEY_BACKHOOK_PROBABILITY, config.backhookProbability)
            putFloat(KEY_BACKHOOK_LENGTH_RATIO, config.backhookLengthRatio)
            // 档位概率
            putFloat(KEY_TIER_1_PROBABILITY, config.tier1Probability)
            putFloat(KEY_TIER_2_PROBABILITY, config.tier2Probability)
            putFloat(KEY_TIER_3_PROBABILITY, config.tier3Probability)
            apply()
        }
    }

    /**
     * 加载配置
     */
    fun loadConfig(context: Context): SwipeConfig {
        val prefs = getPrefs(context)
        return SwipeConfig(
            // 基础配置
            intervalMin = prefs.getFloat(KEY_INTERVAL_MIN, SwipeConfig.DEFAULT_INTERVAL_MIN),
            intervalMax = prefs.getFloat(KEY_INTERVAL_MAX, SwipeConfig.DEFAULT_INTERVAL_MAX),
            startPositionRange = prefs.getFloat(KEY_START_POSITION_RANGE, SwipeConfig.DEFAULT_START_POSITION_RANGE),
            swipeDistanceMin = prefs.getFloat(KEY_SWIPE_DISTANCE_MIN, SwipeConfig.DEFAULT_SWIPE_DISTANCE_MIN),
            swipeDistanceMax = prefs.getFloat(KEY_SWIPE_DISTANCE_MAX, SwipeConfig.DEFAULT_SWIPE_DISTANCE_MAX),
            swipeDurationMin = prefs.getFloat(KEY_SWIPE_DURATION_MIN, SwipeConfig.DEFAULT_SWIPE_DURATION_MIN),
            swipeDurationMax = prefs.getFloat(KEY_SWIPE_DURATION_MAX, SwipeConfig.DEFAULT_SWIPE_DURATION_MAX),
            // 高级配置
            maxSwipeAngle = prefs.getFloat(KEY_MAX_SWIPE_ANGLE, SwipeConfig.DEFAULT_MAX_SWIPE_ANGLE),
            jitterIntensityMin = prefs.getFloat(KEY_JITTER_INTENSITY_MIN, SwipeConfig.DEFAULT_JITTER_INTENSITY_MIN),
            jitterIntensityMax = prefs.getFloat(KEY_JITTER_INTENSITY_MAX, SwipeConfig.DEFAULT_JITTER_INTENSITY_MAX),
            curveIntensityMin = prefs.getFloat(KEY_CURVE_INTENSITY_MIN, SwipeConfig.DEFAULT_CURVE_INTENSITY_MIN),
            curveIntensityMax = prefs.getFloat(KEY_CURVE_INTENSITY_MAX, SwipeConfig.DEFAULT_CURVE_INTENSITY_MAX),
            backhookProbability = prefs.getInt(KEY_BACKHOOK_PROBABILITY, SwipeConfig.DEFAULT_BACKHOOK_PROBABILITY),
            backhookLengthRatio = prefs.getFloat(KEY_BACKHOOK_LENGTH_RATIO, SwipeConfig.DEFAULT_BACKHOOK_LENGTH_RATIO),
            // 档位概率
            tier1Probability = prefs.getFloat(KEY_TIER_1_PROBABILITY, SwipeConfig.DEFAULT_TIER_1_PROBABILITY),
            tier2Probability = prefs.getFloat(KEY_TIER_2_PROBABILITY, SwipeConfig.DEFAULT_TIER_2_PROBABILITY),
            tier3Probability = prefs.getFloat(KEY_TIER_3_PROBABILITY, SwipeConfig.DEFAULT_TIER_3_PROBABILITY)
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

