package com.heartzert.zibubu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heartzert.zibubu.R

/**
 * Zibubu配置页面
 * 允许用户配置各种随机参数
 */
class ZibubuConfigActivity : AppCompatActivity() {

    // 基础配置
    private lateinit var etIntervalMin: EditText
    private lateinit var etIntervalMax: EditText
    private lateinit var etStartPositionRange: EditText
    private lateinit var etSwipeDistanceMin: EditText
    private lateinit var etSwipeDistanceMax: EditText
    private lateinit var etSwipeDurationMin: EditText
    private lateinit var etSwipeDurationMax: EditText
    // 高级配置
    private lateinit var etMaxSwipeAngle: EditText
    private lateinit var etJitterIntensityMin: EditText
    private lateinit var etJitterIntensityMax: EditText
    private lateinit var etCurveIntensityMin: EditText
    private lateinit var etCurveIntensityMax: EditText
    private lateinit var etBackhookProbability: EditText
    private lateinit var etBackhookLengthRatio: EditText
    // 档位概率配置
    private lateinit var etTier1Probability: EditText
    private lateinit var etTier2Probability: EditText
    private lateinit var etTier3Probability: EditText
    // 按钮
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_config)

        initViews()
        loadConfig()
        setupListeners()
    }

    /**
     * 初始化视图
     */
    private fun initViews() {
        // 基础配置
        etIntervalMin = findViewById(R.id.et_interval_min)
        etIntervalMax = findViewById(R.id.et_interval_max)
        etStartPositionRange = findViewById(R.id.et_start_position_range)
        etSwipeDistanceMin = findViewById(R.id.et_swipe_distance_min)
        etSwipeDistanceMax = findViewById(R.id.et_swipe_distance_max)
        etSwipeDurationMin = findViewById(R.id.et_swipe_duration_min)
        etSwipeDurationMax = findViewById(R.id.et_swipe_duration_max)
        // 高级配置
        etMaxSwipeAngle = findViewById(R.id.et_max_swipe_angle)
        etJitterIntensityMin = findViewById(R.id.et_jitter_intensity_min)
        etJitterIntensityMax = findViewById(R.id.et_jitter_intensity_max)
        etCurveIntensityMin = findViewById(R.id.et_curve_intensity_min)
        etCurveIntensityMax = findViewById(R.id.et_curve_intensity_max)
        etBackhookProbability = findViewById(R.id.et_backhook_probability)
        etBackhookLengthRatio = findViewById(R.id.et_backhook_length_ratio)
        // 档位概率配置
        etTier1Probability = findViewById(R.id.et_tier1_probability)
        etTier2Probability = findViewById(R.id.et_tier2_probability)
        etTier3Probability = findViewById(R.id.et_tier3_probability)
        // 按钮
        btnSave = findViewById(R.id.btn_save)
        btnReset = findViewById(R.id.btn_reset)
    }

    /**
     * 加载配置
     */
    private fun loadConfig() {
        val config = SwipeConfigManager.loadConfig(this)
        // 基础配置
        etIntervalMin.setText(config.intervalMin.toString())
        etIntervalMax.setText(config.intervalMax.toString())
        etStartPositionRange.setText(config.startPositionRange.toString())
        etSwipeDistanceMin.setText(config.swipeDistanceMin.toString())
        etSwipeDistanceMax.setText(config.swipeDistanceMax.toString())
        etSwipeDurationMin.setText(config.swipeDurationMin.toString())
        etSwipeDurationMax.setText(config.swipeDurationMax.toString())
        // 高级配置
        etMaxSwipeAngle.setText(config.maxSwipeAngle.toString())
        etJitterIntensityMin.setText(config.jitterIntensityMin.toString())
        etJitterIntensityMax.setText(config.jitterIntensityMax.toString())
        etCurveIntensityMin.setText(config.curveIntensityMin.toString())
        etCurveIntensityMax.setText(config.curveIntensityMax.toString())
        etBackhookProbability.setText(config.backhookProbability.toString())
        etBackhookLengthRatio.setText(config.backhookLengthRatio.toString())
        // 档位概率配置
        etTier1Probability.setText(config.tier1Probability.toString())
        etTier2Probability.setText(config.tier2Probability.toString())
        etTier3Probability.setText(config.tier3Probability.toString())
    }

    /**
     * 设置监听器
     */
    private fun setupListeners() {
        btnSave.setOnClickListener {
            saveConfig()
        }

        btnReset.setOnClickListener {
            resetToDefault()
        }
    }

    /**
     * 保存配置
     */
    private fun saveConfig() {
        val config = SwipeConfig(
            // 基础配置
            intervalMin = etIntervalMin.text.toString().toFloat(),
            intervalMax = etIntervalMax.text.toString().toFloat(),
            startPositionRange = etStartPositionRange.text.toString().toFloat(),
            swipeDistanceMin = etSwipeDistanceMin.text.toString().toFloat(),
            swipeDistanceMax = etSwipeDistanceMax.text.toString().toFloat(),
            swipeDurationMin = etSwipeDurationMin.text.toString().toFloat(),
            swipeDurationMax = etSwipeDurationMax.text.toString().toFloat(),
            // 高级配置
            maxSwipeAngle = etMaxSwipeAngle.text.toString().toFloat(),
            jitterIntensityMin = etJitterIntensityMin.text.toString().toFloat(),
            jitterIntensityMax = etJitterIntensityMax.text.toString().toFloat(),
            curveIntensityMin = etCurveIntensityMin.text.toString().toFloat(),
            curveIntensityMax = etCurveIntensityMax.text.toString().toFloat(),
            backhookProbability = etBackhookProbability.text.toString().toInt(),
            backhookLengthRatio = etBackhookLengthRatio.text.toString().toFloat(),
            // 档位概率配置
            tier1Probability = etTier1Probability.text.toString().toFloat(),
            tier2Probability = etTier2Probability.text.toString().toFloat(),
            tier3Probability = etTier3Probability.text.toString().toFloat()
        )

        if (!config.isValid()) {
            Toast.makeText(this, "配置参数无效，请检查输入范围", Toast.LENGTH_SHORT).show()
            return
        }

        SwipeConfigManager.saveConfig(this, config)

        // 通知服务重新加载配置
        sendBroadcast(Intent(ZibubuAccessibilityService.ACTION_RELOAD_CONFIG))

        Toast.makeText(this, "配置已保存", Toast.LENGTH_SHORT).show()
        finish()
    }

    /**
     * 重置为默认值
     */
    private fun resetToDefault() {
        val defaultConfig = SwipeConfig()
        // 基础配置
        etIntervalMin.setText(defaultConfig.intervalMin.toString())
        etIntervalMax.setText(defaultConfig.intervalMax.toString())
        etStartPositionRange.setText(defaultConfig.startPositionRange.toString())
        etSwipeDistanceMin.setText(defaultConfig.swipeDistanceMin.toString())
        etSwipeDistanceMax.setText(defaultConfig.swipeDistanceMax.toString())
        etSwipeDurationMin.setText(defaultConfig.swipeDurationMin.toString())
        etSwipeDurationMax.setText(defaultConfig.swipeDurationMax.toString())
        // 高级配置
        etMaxSwipeAngle.setText(defaultConfig.maxSwipeAngle.toString())
        etJitterIntensityMin.setText(defaultConfig.jitterIntensityMin.toString())
        etJitterIntensityMax.setText(defaultConfig.jitterIntensityMax.toString())
        etCurveIntensityMin.setText(defaultConfig.curveIntensityMin.toString())
        etCurveIntensityMax.setText(defaultConfig.curveIntensityMax.toString())
        etBackhookProbability.setText(defaultConfig.backhookProbability.toString())
        etBackhookLengthRatio.setText(defaultConfig.backhookLengthRatio.toString())
        // 档位概率配置
        etTier1Probability.setText(defaultConfig.tier1Probability.toString())
        etTier2Probability.setText(defaultConfig.tier2Probability.toString())
        etTier3Probability.setText(defaultConfig.tier3Probability.toString())
        Toast.makeText(this, "已重置为默认值", Toast.LENGTH_SHORT).show()
    }
}


