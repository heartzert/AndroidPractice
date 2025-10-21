package com.heartzert.autoswipe

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.heartzert.autoswipe.R
import kotlin.math.abs

/**
 * 悬浮窗服务
 * 提供悬浮按钮控制自动滑动功能的开关
 */
class FloatingWindowService : Service() {

    private lateinit var windowManager: WindowManager
    private var floatingView: View? = null
    private var isSwipeRunning = false
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())

    private val stateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AutoSwipeAccessibilityService.ACTION_SWIPE_STATE_CHANGED) {
                isSwipeRunning = intent.getBooleanExtra(
                    AutoSwipeAccessibilityService.EXTRA_IS_RUNNING,
                    false
                )
                updateFloatingViewState()
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: service created")

        // 注册广播接收器
        val filter = IntentFilter(AutoSwipeAccessibilityService.ACTION_SWIPE_STATE_CHANGED)
        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            registerReceiver(stateReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(stateReceiver, filter)
        }

        // 初始化状态
        isSwipeRunning = SwipeConfigManager.isEnabled(this)

        // 创建悬浮窗
        createFloatingWindow()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: service destroyed")
        removeFloatingWindow()
        try {
            unregisterReceiver(stateReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "onDestroy: failed to unregister receiver", e)
        }
    }

    /**
     * 创建悬浮窗
     */
    private fun createFloatingWindow() {
        try {
            windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            // 加载悬浮窗布局
            floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_window, null)
            Log.d(TAG, "createFloatingWindow: layout inflated")

            // 设置悬浮窗参数
            val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

            params.gravity = Gravity.TOP or Gravity.END
            params.x = 0
            params.y = 200

            Log.d(TAG, "createFloatingWindow: trying to add view, type=$layoutFlag")

            // 添加到窗口管理器
            windowManager.addView(floatingView, params)
            Log.d(TAG, "createFloatingWindow: view added successfully")

            // 设置拖动和点击监听
            setupFloatingViewListeners(params)

            // 更新初始状态
            updateFloatingViewState()
        } catch (e: Exception) {
            Log.e(TAG, "createFloatingWindow: failed to create floating window", e)
        }
    }

    /**
     * 设置悬浮窗监听器
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setupFloatingViewListeners(params: WindowManager.LayoutParams) {
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isDragging = false

        floatingView?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    // 判断是否为拖动
                    if (abs(deltaX) > 10 || abs(deltaY) > 10) {
                        isDragging = true
                        params.x = initialX + deltaX.toInt()
                        params.y = initialY + deltaY.toInt()
                        windowManager.updateViewLayout(floatingView, params)
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (!isDragging) {
                        // 点击事件 - 切换开关或打开设置
                        if (!isAccessibilityServiceEnabled()) {
                            // 如果无障碍服务未开启，跳转到设置
                            openAccessibilitySettings()
                        } else {
                            // 否则切换滑动开关
                            toggleSwipe()
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }

    /**
     * 更新悬浮窗状态显示
     */
    @SuppressLint("SetTextI18n")
    private fun updateFloatingViewState() {
        val tvStatus = floatingView?.findViewById<TextView>(R.id.tv_status)

        // 检查无障碍服务状态
        val accessibilityEnabled = isAccessibilityServiceEnabled()
        
        if (!accessibilityEnabled) {
            tvStatus?.text = "点击\n开启\n服务"
        } else if (isSwipeRunning) {
            tvStatus?.text = "AUTO\nSWIPE\nON"
        } else {
            tvStatus?.text = "AUTO\nSWIPE\nOFF"
        }
        Log.d(TAG, "updateFloatingViewState: accessibilityEnabled=$accessibilityEnabled, isSwipeRunning=$isSwipeRunning")
    }

    /**
     * 切换滑动开关
     */
    private fun toggleSwipe() {
        // 检查无障碍服务是否运行
        if (!isAccessibilityServiceEnabled()) {
            Log.w(TAG, "toggleSwipe: accessibility service not enabled")
            android.widget.Toast.makeText(
                this,
                "请先在系统设置中开启无障碍服务",
                android.widget.Toast.LENGTH_SHORT
            ).show()
            return
        }

        val action = if (isSwipeRunning) {
            AutoSwipeAccessibilityService.ACTION_STOP_SWIPE
        } else {
            AutoSwipeAccessibilityService.ACTION_START_SWIPE
        }
        
        Log.d(TAG, "toggleSwipe: preparing to send broadcast")
        Log.d(TAG, "  action: $action")
        Log.d(TAG, "  packageName: $packageName")
        
        val intent = Intent(action)
        intent.setPackage(packageName)  // 明确指定包名
        sendBroadcast(intent)
        
        Log.d(TAG, "toggleSwipe: broadcast sent!")
        
        // 延迟检查状态
        handler.postDelayed({
            Log.d(TAG, "toggleSwipe: checking state after 1 second...")
            val newState = SwipeConfigManager.isEnabled(this)
            Log.d(TAG, "toggleSwipe: current state in config = $newState")
        }, 1000)
    }

    /**
     * 检查无障碍服务是否已启用
     */
    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${packageName}/${AutoSwipeAccessibilityService::class.java.name}"
        val settingValue = android.provider.Settings.Secure.getString(
            contentResolver,
            android.provider.Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        val isEnabled = settingValue?.contains(service) == true
        Log.d(TAG, "isAccessibilityServiceEnabled: $isEnabled, service=$service")
        return isEnabled
    }

    /**
     * 打开无障碍设置页面
     */
    private fun openAccessibilitySettings() {
        Log.d(TAG, "openAccessibilitySettings: opening accessibility settings")
        android.widget.Toast.makeText(
            this,
            "请在设置中开启 Auto Swipe Service",
            android.widget.Toast.LENGTH_LONG
        ).show()
        val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    /**
     * 打开配置页面
     */
    private fun openConfigActivity() {
        val intent = Intent(this, SwipeConfigActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    /**
     * 移除悬浮窗
     */
    private fun removeFloatingWindow() {
        floatingView?.let {
            try {
                windowManager.removeView(it)
                Log.d(TAG, "removeFloatingWindow: view removed")
            } catch (e: Exception) {
                Log.e(TAG, "removeFloatingWindow: failed to remove view", e)
            }
            floatingView = null
        }
    }

    companion object {
        private const val TAG = "FloatingWindowService"
    }
}


