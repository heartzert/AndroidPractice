package heartzert.test.all.uitest.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withMatrix
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Created by heartzert on 2024/09/30.
 * Email: heartzert@163.com
 *
 * 手势操作时，不能用gestureDetector的onScroll来计算位移，
 * 因为onScroll必须在手指按下后短时间内移动，才能触发，如果按下超过一定时间再移动，就不会触发onScroll了，操作不够丝滑
 *
 * 所以写一个手势操作的示例view，支持单指拖动，双指缩放加拖动加旋转
 *
 * // TODO: 2024/9/30 旋转中心点还不对
 */
class TouchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var mMatrix = Matrix()

    var mCenterPointF = PointF(0f, 0f)
    var mRotation = 0f
    var mScale = 1f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withMatrix(mMatrix) {
            canvas.drawRect(0f, 0f, 100f, 200f, Paint().apply { color = Color.RED })
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mTouchEvent(event)
    }

    private var lastTouchX = -1f
    private var lastTouchY = -1f

    private var lastMultiTouchCenterX = -1f
    private var lastMultiTouchCenterY = -1f

    private var lastDiagonalDistance = 0f
    private var lastDiagonalDegree = 0f

    private fun mTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (isInBtnArea()) {
                    return false
                }
                lastTouchX = x
                lastTouchY = y
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    lastDiagonalDistance = getDistance(event)
                    lastDiagonalDegree = getRotate(event)

                    lastMultiTouchCenterX = (event.getX(0) + event.getX(1)) / 2
                    lastMultiTouchCenterY = (event.getY(0) + event.getY(1)) / 2
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    transition(x - lastTouchX, y - lastTouchY)
                    lastTouchX = x
                    lastTouchY = y
                } else if (event.pointerCount == 2) {
                    val newDistance = getDistance(event)
                    val dDistance = newDistance / lastDiagonalDistance

                    val curMultiTouchCenterX = (event.getX(0) + event.getX(1)) / 2
                    val curMultiTouchCenterY = (event.getY(0) + event.getY(1)) / 2
                    val dx = curMultiTouchCenterX - lastMultiTouchCenterX
                    val dy = curMultiTouchCenterY - lastMultiTouchCenterY
                    transition(dx, dy)

                    if (dDistance != 1f) {
                        scale(dDistance)
                        if (newDistance > 0) {
                            lastDiagonalDistance = newDistance
                        }
                    }

                    val newDegree: Float = getRotate(event)
                    val dDegree: Float = (newDegree - lastDiagonalDegree).toInt().toFloat()
                    if (abs(dDegree.toDouble()) > MIN_ROTATE_DEGREE) {
                        rotate(dDegree)
                        lastDiagonalDegree = newDegree
                    }

                    lastMultiTouchCenterX = curMultiTouchCenterX
                    lastMultiTouchCenterY = curMultiTouchCenterY

                }
            }

            MotionEvent.ACTION_POINTER_UP -> {
                if (event.pointerCount == 2) {
                    if (event.getPointerId(event.actionIndex) == 0) {
                        lastTouchX = event.getX(1)
                        lastTouchY = event.getY(1)
                    } else {
                        lastTouchX = event.getX(0)
                        lastTouchY = event.getY(0)
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                lastTouchX = -1f
                lastTouchY = -1f
                lastDiagonalDegree = 0f
                lastDiagonalDistance = 0f
                lastMultiTouchCenterX = -1f
                lastMultiTouchCenterY = -1f
            }
        }
        invalidate()
        return true
    }

    private fun isInBtnArea(): Boolean {
        return false
    }

    private fun scale(factor: Float) {
        mScale *= factor
        mMatrix.postScale(factor, factor, mCenterPointF.x, mCenterPointF.y)
    }

    private fun rotate(degree: Float) {
        mRotation += degree
        mMatrix.postRotate(degree, mCenterPointF.x, mCenterPointF.y)
    }

    private fun transition(dx: Float, dy: Float) {
        mCenterPointF.x += dx
        mCenterPointF.y += dy
        mMatrix.postTranslate(dx, dy)
    }

    private fun getRotate(event: MotionEvent): Float {
        val dx = event.getX(1) - event.getX(0)
        val dy = event.getY(1) - event.getY(0)
        return Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
    }

    private fun getDistance(event: MotionEvent): Float {
        val dx = event.getX(0) - event.getX(1)
        val dy = event.getY(0) - event.getY(1)
        return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    companion object {

        //每次旋转的最小度数
        private const val MIN_ROTATE_DEGREE = 0.001f
    }
}
