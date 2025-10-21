package com.heartzert.autoswipe

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.heartzert.autoswipe.R

/**
 * 无障碍服务测试页面
 */
class AccessibilityTestActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvFloatingWindowStatus: TextView
    private lateinit var btnOpenSettings: Button
    private lateinit var btnOpenFloatingWindow: Button
    private lateinit var btnCloseFloatingWindow: Button
    private lateinit var btnOpenConfig: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_test)

        initViews()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        updateServiceStatus()
        updateFloatingWindowStatus()
    }

    /**
     * 初始化视图
     */
    private fun initViews() {
        tvStatus = findViewById(R.id.tv_status)
        tvFloatingWindowStatus = findViewById(R.id.tv_floating_window_status)
        btnOpenSettings = findViewById(R.id.btn_open_settings)
        btnOpenFloatingWindow = findViewById(R.id.btn_open_floating_window)
        btnCloseFloatingWindow = findViewById(R.id.btn_close_floating_window)
        btnOpenConfig = findViewById(R.id.btn_open_config)
    }

    /**
     * 设置监听器
     */
    private fun setupListeners() {
        btnOpenSettings.setOnClickListener {
            openAccessibilitySettings()
        }

        btnOpenFloatingWindow.setOnClickListener {
            openFloatingWindow()
        }

        btnCloseFloatingWindow.setOnClickListener {
            closeFloatingWindow()
        }

        btnOpenConfig.setOnClickListener {
            openConfigActivity()
        }

    }

    /**
     * 打开无障碍设置页面
     */
    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    /**
     * 打开悬浮窗
     */
    private fun openFloatingWindow() {
        // 检查悬浮窗权限
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "请先授予悬浮窗权限", Toast.LENGTH_SHORT).show()
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:$packageName".toUri()
            )
            startActivity(intent)
            return
        }

        // 启动悬浮窗服务
        try {
            val intent = Intent(this, FloatingWindowService::class.java)
            startService(intent)
            Toast.makeText(this, "悬浮窗已开启", Toast.LENGTH_SHORT).show()
            android.util.Log.d("AccessibilityTestAct", "openFloatingWindow: service started")
        } catch (e: Exception) {
            Toast.makeText(this, "启动悬浮窗失败: ${e.message}", Toast.LENGTH_SHORT).show()
            android.util.Log.e("AccessibilityTestAct", "openFloatingWindow: failed to start service", e)
        }
    }

    /**
     * 关闭悬浮窗
     */
    private fun closeFloatingWindow() {
        val intent = Intent(this, FloatingWindowService::class.java)
        stopService(intent)
        Toast.makeText(this, "悬浮窗已关闭", Toast.LENGTH_SHORT).show()
        android.util.Log.d("AccessibilityTestAct", "closeFloatingWindow: service stopped")
    }

    /**
     * 打开配置页面
     */
    private fun openConfigActivity() {
        val intent = Intent(this, SwipeConfigActivity::class.java)
        startActivity(intent)
    }

    /**
     * 更新服务状态
     */
    private fun updateServiceStatus() {
        val isEnabled = isAccessibilityServiceEnabled()
        tvStatus.text = if (isEnabled) {
            "无障碍服务状态：✓ 已启用\n\n服务已开启，可以使用悬浮窗控制滑动功能"
        } else {
            "无障碍服务状态：✗ 未启用\n\n请点击下方按钮进入系统设置，开启无障碍服务"
        }
    }

    /**
     * 更新悬浮窗状态
     */
    private fun updateFloatingWindowStatus() {
        val hasPermission = Settings.canDrawOverlays(this)

        tvFloatingWindowStatus.text = if (hasPermission) {
            "悬浮窗权限：✓ 已授权"
        } else {
            "悬浮窗权限：✗ 未授权"
        }
    }

    /**
     * 检查无障碍服务是否已启用
     */
    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${packageName}/${AutoSwipeAccessibilityService::class.java.name}"
        val settingValue = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return settingValue?.contains(service) == true
    }
}
