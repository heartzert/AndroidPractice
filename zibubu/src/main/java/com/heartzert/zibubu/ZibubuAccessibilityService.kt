package com.heartzert.zibubu

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
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Zibubu无障碍服务
 * 支持随机间隔、随机起始位置、随机滑动距离和随机滑动时长
 */
class ZibubuAccessibilityService : AccessibilityService() {

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

        val intervalInfo = config.getRandomIntervalWithInfo()
        Log.d(TAG, "scheduleNextSwipe: 【档位${intervalInfo.tier}】base=${String.format("%.2f", intervalInfo.baseSeconds)}s, " +
                "multiplier=${intervalInfo.multiplier}x, final=${intervalInfo.finalMillis}ms (${String.format("%.2f", intervalInfo.finalMillis / 1000f)}s)")

        handler.postDelayed({
            performSwipe()
        }, intervalInfo.finalMillis)
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
        
        // 获取随机倾角（-15度到+15度）
        val angle = config.getRandomAngle()
        val angleRadians = Math.toRadians(angle.toDouble())
        
        // 根据倾角计算偏移量
        // 水平偏移 = 距离 × sin(角度)
        // 垂直偏移 = 距离 × cos(角度)
        val horizontalOffset = (swipeDistancePixels * Math.sin(angleRadians)).toFloat()
        val verticalOffset = (swipeDistancePixels * Math.cos(angleRadians)).toFloat()

        // 确定滑动终点
        val endX: Float
        val endY: Float
        if (isSwipeUp) {
            // 上划：向上移动 + 随机倾斜
            endX = startX + horizontalOffset
            endY = startY - verticalOffset
            Log.d(TAG, "performSwipe: swipe up from ($startX, $startY) to ($endX, $endY), " +
                    "distance=${swipeDistanceDp}dp, angle=${String.format("%.1f", angle)}°")
        } else {
            // 下滑：向下移动 + 随机倾斜
            endX = startX + horizontalOffset
            endY = startY + verticalOffset
            Log.d(TAG, "performSwipe: swipe down from ($startX, $startY) to ($endX, $endY), " +
                    "distance=${swipeDistanceDp}dp, angle=${String.format("%.1f", angle)}°")
        }

        // 随机滑动时长
        val duration = config.getRandomDuration()
        
        // 获取随机插值器
        val interpolatorType = config.getRandomInterpolatorType()
        Log.d(TAG, "performSwipe: duration=${duration}ms, interpolator=${interpolatorType.description}")

        // 创建应用插值器的滑动路径
        val path = createInterpolatedPath(startX, startY, endX, endY, interpolatorType)

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
                    Log.d(TAG, "onCompleted: gesture completed, scheduling next swipe")
                    
                    // 手势完成后才调度下一次滑动，确保不会重叠
                    scheduleNextSwipe()
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    super.onCancelled(gestureDescription)
                    Log.w(TAG, "onCancelled: gesture cancelled, scheduling next swipe anyway")
                    
                    // 即使手势被取消，也调度下一次（避免停止运行）
                    scheduleNextSwipe()
                }
            },
            null
        )

        if (!result) {
            Log.e(TAG, "performSwipe: dispatch gesture failed, scheduling next swipe")
            // 如果分发失败，也调度下一次
            scheduleNextSwipe()
        }

        // 切换滑动方向
        isSwipeUp = !isSwipeUp
    }
    
    /**
     * 创建应用插值器的路径（带抖动和曲度）
     * 通过在起点和终点之间插入多个中间点，并对每个点应用插值器来实现速率变化
     * 同时添加随机抖动和曲度，模拟真实人手滑动
     * 
     * @param startX 起始X坐标
     * @param startY 起始Y坐标
     * @param endX 结束X坐标
     * @param endY 结束Y坐标
     * @param interpolatorType 插值器类型
     * @return 应用了插值器、抖动和曲度的路径
     */
    private fun createInterpolatedPath(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        interpolatorType: InterpolatorType
    ): Path {
        val path = Path()
        path.moveTo(startX, startY)
        
        val interpolator = interpolatorType.interpolator
        
        // 计算滑动方向和距离
        val dx = endX - startX
        val dy = endY - startY
        val distance = sqrt(dx * dx + dy * dy)
        
        // 计算垂直方向的单位向量（用于添加抖动和曲度）
        // 垂直方向 = 旋转90度
        val perpX = -dy / distance  // 垂直方向X分量
        val perpY = dx / distance   // 垂直方向Y分量
        
        // 随机生成曲度参数
        // 曲度控制点在路径中间（40%-60%之间）
        val curvePosition = 0.4f + Random.nextFloat() * 0.2f  // 0.4 ~ 0.6
        val curveIntensity = config.curveIntensityMin + 
                Random.nextFloat() * (config.curveIntensityMax - config.curveIntensityMin)
        val curveDirection = if (Random.nextBoolean()) 1f else -1f  // 随机向左或向右弯曲
        
        // 随机生成抖动强度
        val jitterIntensity = config.jitterIntensityMin + 
                Random.nextFloat() * (config.jitterIntensityMax - config.jitterIntensityMin)
        
        // 判断是否添加回勾
        val hasBackhook = Random.nextInt(100) < config.backhookProbability
        
        Log.d(TAG, "createInterpolatedPath: curve at ${String.format("%.0f", curvePosition * 100)}%, " +
                "curveIntensity=${String.format("%.1f", curveIntensity)}px, " +
                "jitterIntensity=${String.format("%.1f", jitterIntensity)}px, " +
                "backhook=${if (hasBackhook) "YES" else "NO"}")
        
        // 将路径分成多个小段，每段应用插值器、抖动和曲度
        val segments = 20
        
        for (i in 1..segments) {
            // 线性进度：0 到 1
            val linearProgress = i.toFloat() / segments
            
            // 应用插值器得到实际进度
            val interpolatedProgress = interpolator.getInterpolation(linearProgress)
            
            // 根据插值后的进度计算基础坐标
            var currentX = startX + dx * interpolatedProgress
            var currentY = startY + dy * interpolatedProgress
            
            // 添加曲度：在路径中段添加较大的偏移，形成曲线
            // 使用高斯曲线，让中间偏移最大，两端偏移小
            val distanceFromCurve = abs(linearProgress - curvePosition)
            val curveWeight = exp((-distanceFromCurve * distanceFromCurve / 0.05).toDouble()).toFloat() // 高斯函数
            val curveOffset = curveIntensity * curveWeight * curveDirection
            
            currentX += perpX * curveOffset
            currentY += perpY * curveOffset
            
            // 添加随机抖动：每个点都有小幅度的随机偏移
            val jitterOffset = (Random.nextFloat() - 0.5f) * 2 * jitterIntensity
            currentX += perpX * jitterOffset
            currentY += perpY * jitterOffset
            
            path.lineTo(currentX, currentY)
        }
        
        // 添加回勾：在滑动结束时往反方向回勾
        if (hasBackhook) {
            val backhookLength = distance * config.backhookLengthRatio
            
            // 回勾分成3个小段，保持平滑和抖动
            val backhookSegments = 3
            
            for (i in 1..backhookSegments) {
                // 反向进度：从终点往起点方向
                val backhookProgress = i.toFloat() / backhookSegments
                
                // 应用插值器使回勾也有速率变化（减速效果更自然）
                val interpolatedBackhookProgress = interpolator.getInterpolation(backhookProgress)
                
                // 计算回勾点的基础坐标（反向移动）
                var backhookX = endX - dx / distance * backhookLength * interpolatedBackhookProgress
                var backhookY = endY - dy / distance * backhookLength * interpolatedBackhookProgress
                
                // 回勾也添加轻微抖动，使其更真实
                val backhookJitter = (Random.nextFloat() - 0.5f) * 2 * jitterIntensity * 0.5f  // 回勾抖动减半
                backhookX += perpX * backhookJitter
                backhookY += perpY * backhookJitter
                
                path.lineTo(backhookX, backhookY)
            }
            
            Log.d(TAG, "createInterpolatedPath: added backhook, length=${String.format("%.1f", backhookLength)}px")
        }
        
        return path
    }

    companion object {
        private const val TAG = "AutoSwipeService"

        // 广播Action
        const val ACTION_START_SWIPE = "com.heartzert.zibubu.ACTION_START"
        const val ACTION_STOP_SWIPE = "com.heartzert.zibubu.ACTION_STOP"
        const val ACTION_RELOAD_CONFIG = "com.heartzert.zibubu.ACTION_RELOAD_CONFIG"
        const val ACTION_SWIPE_STATE_CHANGED = "com.heartzert.zibubu.ACTION_STATE_CHANGED"
        const val EXTRA_IS_RUNNING = "is_running"
    }
}

