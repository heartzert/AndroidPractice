package com.heartzert.autoswipe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heartzert.autoswipe.R

/**
 * 滑动配置页面
 * 允许用户配置各种随机参数
 */
class SwipeConfigActivity : AppCompatActivity() {

    private lateinit var etIntervalMin: EditText
    private lateinit var etIntervalMax: EditText
    private lateinit var etStartPositionRange: EditText
    private lateinit var etSwipeDistanceMin: EditText
    private lateinit var etSwipeDistanceMax: EditText
    private lateinit var etSwipeDurationMin: EditText
    private lateinit var etSwipeDurationMax: EditText
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
        etIntervalMin = findViewById(R.id.et_interval_min)
        etIntervalMax = findViewById(R.id.et_interval_max)
        etStartPositionRange = findViewById(R.id.et_start_position_range)
        etSwipeDistanceMin = findViewById(R.id.et_swipe_distance_min)
        etSwipeDistanceMax = findViewById(R.id.et_swipe_distance_max)
        etSwipeDurationMin = findViewById(R.id.et_swipe_duration_min)
        etSwipeDurationMax = findViewById(R.id.et_swipe_duration_max)
        btnSave = findViewById(R.id.btn_save)
        btnReset = findViewById(R.id.btn_reset)
    }

    /**
     * 加载配置
     */
    private fun loadConfig() {
        val config = SwipeConfigManager.loadConfig(this)
        etIntervalMin.setText(config.intervalMin.toString())
        etIntervalMax.setText(config.intervalMax.toString())
        etStartPositionRange.setText(config.startPositionRange.toString())
        etSwipeDistanceMin.setText(config.swipeDistanceMin.toString())
        etSwipeDistanceMax.setText(config.swipeDistanceMax.toString())
        etSwipeDurationMin.setText(config.swipeDurationMin.toString())
        etSwipeDurationMax.setText(config.swipeDurationMax.toString())
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
            intervalMin = etIntervalMin.text.toString().toFloat(),
            intervalMax = etIntervalMax.text.toString().toFloat(),
            startPositionRange = etStartPositionRange.text.toString().toFloat(),
            swipeDistanceMin = etSwipeDistanceMin.text.toString().toFloat(),
            swipeDistanceMax = etSwipeDistanceMax.text.toString().toFloat(),
            swipeDurationMin = etSwipeDurationMin.text.toString().toFloat(),
            swipeDurationMax = etSwipeDurationMax.text.toString().toFloat()
        )

        if (!config.isValid()) {
            Toast.makeText(this, "配置参数无效，请检查", Toast.LENGTH_SHORT).show()
            return
        }

        SwipeConfigManager.saveConfig(this, config)

        // 通知服务重新加载配置
        sendBroadcast(Intent(AutoSwipeAccessibilityService.ACTION_RELOAD_CONFIG))

        Toast.makeText(this, "配置已保存", Toast.LENGTH_SHORT).show()
        finish()
    }

    /**
     * 重置为默认值
     */
    private fun resetToDefault() {
        val defaultConfig = SwipeConfig()
        etIntervalMin.setText(defaultConfig.intervalMin.toString())
        etIntervalMax.setText(defaultConfig.intervalMax.toString())
        etStartPositionRange.setText(defaultConfig.startPositionRange.toString())
        etSwipeDistanceMin.setText(defaultConfig.swipeDistanceMin.toString())
        etSwipeDistanceMax.setText(defaultConfig.swipeDistanceMax.toString())
        etSwipeDurationMin.setText(defaultConfig.swipeDurationMin.toString())
        etSwipeDurationMax.setText(defaultConfig.swipeDurationMax.toString())
        Toast.makeText(this, "已重置为默认值", Toast.LENGTH_SHORT).show()
    }
}


