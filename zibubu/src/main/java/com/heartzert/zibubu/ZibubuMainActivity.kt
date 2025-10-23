package com.heartzert.zibubu

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.heartzert.zibubu.R

/**
 * Zibubu主页面
 */
class ZibubuMainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvFloatingWindowStatus: TextView
    private lateinit var tvSwipeStatus: TextView
    private lateinit var btnOpenSettings: Button
    private lateinit var btnOpenFloatingWindow: Button
    private lateinit var btnCloseFloatingWindow: Button
    private lateinit var btnStopSwipe: Button
    private lateinit var btnOpenConfig: Button
    
    // 滑动状态接收器
    private val swipeStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isRunning = intent?.getBooleanExtra(ZibubuAccessibilityService.EXTRA_IS_RUNNING, false) ?: false
            updateSwipeStatus(isRunning)
        }
    }

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
        
        // 注册广播接收器监听滑动状态
        val filter = IntentFilter(ZibubuAccessibilityService.ACTION_SWIPE_STATE_CHANGED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(swipeStateReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(swipeStateReceiver, filter)
        }
        
        // 初始化滑动状态
        val isEnabled = SwipeConfigManager.isEnabled(this)
        updateSwipeStatus(isEnabled)
    }
    
    override fun onPause() {
        super.onPause()
        // 取消注册广播接收器
        try {
            unregisterReceiver(swipeStateReceiver)
        } catch (e: Exception) {
            // 忽略异常
        }
    }

    /**
     * 初始化视图
     */
    private fun initViews() {
        tvStatus = findViewById(R.id.tv_status)
        tvFloatingWindowStatus = findViewById(R.id.tv_floating_window_status)
        tvSwipeStatus = findViewById(R.id.tv_swipe_status)
        btnOpenSettings = findViewById(R.id.btn_open_settings)
        btnOpenFloatingWindow = findViewById(R.id.btn_open_floating_window)
        btnCloseFloatingWindow = findViewById(R.id.btn_close_floating_window)
        btnStopSwipe = findViewById(R.id.btn_stop_swipe)
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
        
        btnStopSwipe.setOnClickListener {
            stopSwipe()
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
            val intent = Intent(this, ZibubuWindowService::class.java)
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
        val intent = Intent(this, ZibubuWindowService::class.java)
        stopService(intent)
        Toast.makeText(this, "悬浮窗已关闭", Toast.LENGTH_SHORT).show()
        android.util.Log.d("AccessibilityTestAct", "closeFloatingWindow: service stopped")
    }

    /**
     * 停止滑动
     */
    private fun stopSwipe() {
        // 发送停止广播（无论服务是否启用都发送，确保状态一致）
        sendBroadcast(Intent(ZibubuAccessibilityService.ACTION_STOP_SWIPE))
        
        // 同时清除本地状态
        SwipeConfigManager.setEnabled(this, false)
        updateSwipeStatus(false)
        
        Toast.makeText(this, "已停止滑动", Toast.LENGTH_SHORT).show()
        android.util.Log.d("AccessibilityTestAct", "stopSwipe: broadcast sent and local state cleared")
    }
    
    /**
     * 打开配置页面
     */
    private fun openConfigActivity() {
        val intent = Intent(this, ZibubuConfigActivity::class.java)
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
     * 更新滑动状态
     */
    private fun updateSwipeStatus(isRunning: Boolean) {
        tvSwipeStatus.text = if (isRunning) {
            "滑动状态：✓ 运行中"
        } else {
            "滑动状态：✗ 未运行"
        }
        tvSwipeStatus.setTextColor(if (isRunning) 0xFF4CAF50.toInt() else 0xFF9E9E9E.toInt())
    }

    /**
     * 检查无障碍服务是否已启用
     */
    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${packageName}/${ZibubuAccessibilityService::class.java.name}"
        val settingValue = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return settingValue?.contains(service) == true
    }
}
