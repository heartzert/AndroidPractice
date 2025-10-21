package com.heartzert.autoswipe

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kotlin.random.Random

/**
 * 自动滑动无障碍服务
 * 支持随机间隔、随机起始位置、随机滑动距离和随机滑动时长
 */
class AutoSwipeAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isSwipeUp = true
    private var isRunning = false
    private lateinit var config: SwipeConfig

    private val controlReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "onReceive: received broadcast action=${intent?.action}")
            when (intent?.action) {
                ACTION_START_SWIPE -> {
                    Log.d(TAG, "onReceive: start swipe command received")
                    startAutoSwipe()
                }

                ACTION_STOP_SWIPE -> {
                    Log.d(TAG, "onReceive: stop swipe command received")
                    stopAutoSwipe()
                }

                ACTION_RELOAD_CONFIG -> {
                    Log.d(TAG, "onReceive: reload config command received")
                    reloadConfig()
                }
                else -> {
                    Log.w(TAG, "onReceive: unknown action ${intent?.action}")
                }
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "==========================================")
        Log.d(TAG, "=== onServiceConnected: Accessibility service connected ===")
        Log.d(TAG, "==========================================")

        try {
            // 注册广播接收器
            val filter = IntentFilter().apply {
                addAction(ACTION_START_SWIPE)
                addAction(ACTION_STOP_SWIPE)
                addAction(ACTION_RELOAD_CONFIG)
            }
            
            Log.d(TAG, "onServiceConnected: registering receiver for actions:")
            Log.d(TAG, "  - $ACTION_START_SWIPE")
            Log.d(TAG, "  - $ACTION_STOP_SWIPE")
            Log.d(TAG, "  - $ACTION_RELOAD_CONFIG")
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(controlReceiver, filter, android.content.Context.RECEIVER_EXPORTED)
                Log.d(TAG, "onServiceConnected: receiver registered with RECEIVER_EXPORTED")
            } else {
                registerReceiver(controlReceiver, filter)
                Log.d(TAG, "onServiceConnected: receiver registered")
            }
            
            Log.d(TAG, "onServiceConnected: receiver registration successful!")

            // 加载配置
            reloadConfig()

            // 如果之前是启用状态，则自动开始
            val wasEnabled = SwipeConfigManager.isEnabled(this)
            Log.d(TAG, "onServiceConnected: previous enabled state = $wasEnabled")
            if (wasEnabled) {
                startAutoSwipe()
            }
            
            Log.d(TAG, "==========================================")
            Log.d(TAG, "onServiceConnected: initialization complete")
            Log.d(TAG, "==========================================")
        } catch (e: Exception) {
            Log.e(TAG, "onServiceConnected: failed to initialize", e)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 不需要处理任何事件
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt: service interrupted")
        stopAutoSwipe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: service destroyed")
        stopAutoSwipe()
        try {
            unregisterReceiver(controlReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "onDestroy: failed to unregister receiver", e)
        }
    }

    /**
     * 重新加载配置
     */
    private fun reloadConfig() {
        config = SwipeConfigManager.loadConfig(this)
        Log.d(TAG, "reloadConfig: $config")
    }

    /**
     * 开始自动滑动
     */
    private fun startAutoSwipe() {
        if (isRunning) {
            return
        }
        isRunning = true
        SwipeConfigManager.setEnabled(this, true)
        Log.d(TAG, "startAutoSwipe: start auto swipe")
        scheduleNextSwipe()

        // 通知悬浮窗更新状态
        sendBroadcast(Intent(ACTION_SWIPE_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RUNNING, true)
        })
    }

    /**
     * 停止自动滑动
     */
    private fun stopAutoSwipe() {
        isRunning = false
        SwipeConfigManager.setEnabled(this, false)
        handler.removeCallbacksAndMessages(null)
        Log.d(TAG, "stopAutoSwipe: stop auto swipe")

        // 通知悬浮窗更新状态
        sendBroadcast(Intent(ACTION_SWIPE_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_RUNNING, false)
        })
    }

    /**
     * 调度下一次滑动
     */
    private fun scheduleNextSwipe() {
        if (!isRunning) {
            return
        }

        val interval = config.getRandomInterval()
        Log.d(TAG, "scheduleNextSwipe: next swipe in ${interval}ms")

        handler.postDelayed({
            performSwipe()
            scheduleNextSwipe()
        }, interval)
    }

    /**
     * 执行滑动操作
     */
    private fun performSwipe() {
        // 获取屏幕尺寸
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val density = displayMetrics.density

        // 计算屏幕中心点
        val centerX = screenWidth / 2f
        val centerY = screenHeight / 2f

        // 在屏幕中心的指定dp矩形内随机选择起始位置
        val rangePixels = config.startPositionRange * density
        val randomOffsetX = (Random.nextFloat() - 0.5f) * rangePixels
        val randomOffsetY = (Random.nextFloat() - 0.5f) * rangePixels
        val startX = centerX + randomOffsetX
        val startY = centerY + randomOffsetY

        // 随机滑动距离（dp转像素）
        val swipeDistanceDp = config.swipeDistanceMin +
                Random.nextFloat() * (config.swipeDistanceMax - config.swipeDistanceMin)
        val swipeDistancePixels = swipeDistanceDp * density

        // 确定滑动终点
        val endX: Float
        val endY: Float
        if (isSwipeUp) {
            // 上划
            endX = startX
            endY = startY - swipeDistancePixels
            Log.d(TAG, "performSwipe: swipe up from ($startX, $startY) to ($endX, $endY), distance=${swipeDistanceDp}dp")
        } else {
            // 下滑
            endX = startX
            endY = startY + swipeDistancePixels
            Log.d(TAG, "performSwipe: swipe down from ($startX, $startY) to ($endX, $endY), distance=${swipeDistanceDp}dp")
        }

        // 随机滑动时长
        val duration = config.getRandomDuration()
        Log.d(TAG, "performSwipe: duration=${duration}ms")

        // 创建滑动路径
        val path = Path()
        path.moveTo(startX, startY)
        path.lineTo(endX, endY)

        // 创建手势描述
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(
            GestureDescription.StrokeDescription(
                path,
                0,
                duration
            )
        )

        // 执行手势
        val result = dispatchGesture(
            gestureBuilder.build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                    Log.d(TAG, "onCompleted: gesture completed")
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    super.onCancelled(gestureDescription)
                    Log.w(TAG, "onCancelled: gesture cancelled")
                }
            },
            null
        )

        if (!result) {
            Log.e(TAG, "performSwipe: dispatch gesture failed")
        }

        // 切换滑动方向
        isSwipeUp = !isSwipeUp
    }

    companion object {
        private const val TAG = "AutoSwipeService"

        // 广播Action
        const val ACTION_START_SWIPE = "com.heartzert.autoswipe.ACTION_START_SWIPE"
        const val ACTION_STOP_SWIPE = "com.heartzert.autoswipe.ACTION_STOP_SWIPE"
        const val ACTION_RELOAD_CONFIG = "com.heartzert.autoswipe.ACTION_RELOAD_CONFIG"
        const val ACTION_SWIPE_STATE_CHANGED = "com.heartzert.autoswipe.ACTION_SWIPE_STATE_CHANGED"
        const val EXTRA_IS_RUNNING = "is_running"
    }
}

