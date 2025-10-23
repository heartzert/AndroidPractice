package com.heartzert.zibubu

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
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import kotlin.math.abs

/**
 * Zibubu悬浮窗服务
 * 提供悬浮按钮控制功能开关
 */
class ZibubuWindowService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var vibrator: Vibrator
    private var floatingView: View? = null
    private var closeButtonView: View? = null
    private var isSwipeRunning = false
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    
    // 长按相关
    private var longPressRunnable: Runnable? = null
    private var isLongPressed = false

    private val stateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ZibubuAccessibilityService.ACTION_SWIPE_STATE_CHANGED) {
                isSwipeRunning = intent.getBooleanExtra(
                    ZibubuAccessibilityService.EXTRA_IS_RUNNING,
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

        // 初始化震动器
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // 注册广播接收器
        val filter = IntentFilter(ZibubuAccessibilityService.ACTION_SWIPE_STATE_CHANGED)
        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            registerReceiver(stateReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(stateReceiver, filter)
        }

        // 初始化状态
        isSwipeRunning = SwipeConfigManager.isEnabled(this)

        // 创建悬浮窗
        createFloatingWindow()
        
        // 创建关闭按钮（初始隐藏）
        createCloseButton()
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
        removeCloseButton()
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

            params.gravity = Gravity.TOP or Gravity.START
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
     * 创建关闭按钮
     */
    private fun createCloseButton() {
        try {
            closeButtonView = LayoutInflater.from(this).inflate(R.layout.layout_close_button, null)

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

            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            params.y = 50

            // 初始隐藏
            closeButtonView?.visibility = View.GONE
            
            windowManager.addView(closeButtonView, params)
            Log.d(TAG, "createCloseButton: close button created")
        } catch (e: Exception) {
            Log.e(TAG, "createCloseButton: failed to create close button", e)
        }
    }

    /**
     * 显示关闭按钮
     */
    private fun showCloseButton() {
        closeButtonView?.visibility = View.VISIBLE
    }

    /**
     * 隐藏关闭按钮
     */
    private fun hideCloseButton() {
        closeButtonView?.visibility = View.GONE
    }

    /**
     * 震动反馈
     */
    private fun vibrate(duration: Long = 20) {
        try {
            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(duration)
                }
                Log.d(TAG, "vibrate: vibrated for ${duration}ms")
            }
        } catch (e: Exception) {
            Log.e(TAG, "vibrate: failed to vibrate", e)
        }
    }

    /**
     * 检查是否在关闭按钮范围内
     */
    private fun isInCloseButtonArea(x: Float, y: Float): Boolean {
        val closeButton = closeButtonView ?: return false
        if (closeButton.visibility != View.VISIBLE) return false

        val location = IntArray(2)
        closeButton.getLocationOnScreen(location)
        val buttonX = location[0].toFloat()
        val buttonY = location[1].toFloat()
        val buttonWidth = closeButton.width.toFloat()
        val buttonHeight = closeButton.height.toFloat()

        // 扩大触发区域
        val expandArea = 150f
        val isInArea = x >= buttonX - expandArea &&
               x <= buttonX + buttonWidth + expandArea &&
               y >= buttonY - expandArea &&
               y <= buttonY + buttonHeight + expandArea
        
        if (isInArea) {
            Log.d(TAG, "isInCloseButtonArea: true, touch=($x,$y), button=($buttonX,$buttonY,${buttonX+buttonWidth},${buttonY+buttonHeight})")
        }
        
        return isInArea
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
        var downTime = 0L

        floatingView?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    isLongPressed = false
                    downTime = System.currentTimeMillis()

                    // 设置长按检测
                    longPressRunnable = Runnable {
                        if (!isDragging) {
                            isLongPressed = true
                            onLongPress()
                        }
                    }
                    handler.postDelayed(longPressRunnable!!, LONG_PRESS_TIMEOUT)
                    
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    // 判断是否为拖动
                    if (abs(deltaX) > 10 || abs(deltaY) > 10) {
                        isDragging = true
                        // 取消长按
                        longPressRunnable?.let { handler.removeCallbacks(it) }
                        
                        // 显示关闭按钮
                        showCloseButton()
                        
                        // 更新位置
                        params.x = initialX + deltaX.toInt()
                        params.y = initialY + deltaY.toInt()
                        windowManager.updateViewLayout(floatingView, params)
                        
                        // 检查是否在关闭按钮区域
                        if (isInCloseButtonArea(event.rawX, event.rawY)) {
                            // 放大关闭按钮提示用户
                            closeButtonView?.scaleX = 1.2f
                            closeButtonView?.scaleY = 1.2f
                        } else {
                            closeButtonView?.scaleX = 1.0f
                            closeButtonView?.scaleY = 1.0f
                        }
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    // 取消长按
                    longPressRunnable?.let { handler.removeCallbacks(it) }
                    
                    Log.d(TAG, "ACTION_UP: isDragging=$isDragging, isLongPressed=$isLongPressed, rawX=${event.rawX}, rawY=${event.rawY}")
                    
                    // 检查是否释放在关闭按钮上
                    if (isDragging && isInCloseButtonArea(event.rawX, event.rawY)) {
                        Log.d(TAG, "onTouchListener: closing floating window - user released on close button")
                        
                        // 震动反馈
                        vibrate(20)
                        
                        android.widget.Toast.makeText(
                            this,
                            "悬浮窗已关闭",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        
                        // 延迟关闭，让用户看到Toast
                        handler.postDelayed({
                            stopSelf()
                        }, 100)
                        
                        // 先隐藏关闭按钮
                        hideCloseButton()
                        closeButtonView?.scaleX = 1.0f
                        closeButtonView?.scaleY = 1.0f
                        
                        isDragging = false
                        isLongPressed = false
                        return@setOnTouchListener true
                    }
                    
                    // 隐藏关闭按钮
                    hideCloseButton()
                    closeButtonView?.scaleX = 1.0f
                    closeButtonView?.scaleY = 1.0f
                    
                    // 如果不是拖动也不是长按，则视为点击
                    if (!isDragging && !isLongPressed) {
                        val clickDuration = System.currentTimeMillis() - downTime
                        if (clickDuration < LONG_PRESS_TIMEOUT) {
                            // 点击事件 - 切换开关或打开设置
                            if (!isAccessibilityServiceEnabled()) {
                                openAccessibilitySettings()
                            } else {
                                toggleSwipe()
                            }
                        }
                    }
                    
                    isDragging = false
                    isLongPressed = false
                    true
                }

                else -> false
            }
        }
    }

    /**
     * 长按事件处理
     */
    private fun onLongPress() {
        Log.d(TAG, "onLongPress: opening main activity")
        
        // 震动反馈
        vibrate(20)
        
        android.widget.Toast.makeText(
            this,
            "打开应用",
            android.widget.Toast.LENGTH_SHORT
        ).show()
        
        // 打开主Activity
        val intent = Intent(this, ZibubuMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
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
            tvStatus?.text = "ON"
        } else {
            tvStatus?.text = "OFF"
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
            ZibubuAccessibilityService.ACTION_STOP_SWIPE
        } else {
            ZibubuAccessibilityService.ACTION_START_SWIPE
        }
        
        Log.d(TAG, "toggleSwipe: preparing to send broadcast")
        Log.d(TAG, "  action: $action")
        Log.d(TAG, "  packageName: $packageName")
        
        val intent = Intent(action)
        intent.setPackage(packageName)
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
        val service = "${packageName}/${ZibubuAccessibilityService::class.java.name}"
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

    /**
     * 移除关闭按钮
     */
    private fun removeCloseButton() {
        closeButtonView?.let {
            try {
                windowManager.removeView(it)
                Log.d(TAG, "removeCloseButton: close button removed")
            } catch (e: Exception) {
                Log.e(TAG, "removeCloseButton: failed to remove close button", e)
            }
            closeButtonView = null
        }
    }

    companion object {
        private const val TAG = "ZibubuWindowService"
        private const val LONG_PRESS_TIMEOUT = 500L  // 长按超时时间（毫秒）
    }
}
