package heartzert.lib.widget

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener


/**
 * Created by heartzert on 2020/10/29.
 * Email: heartzert@163.com
 *
 * 手指滑动、抬起后记为一次滑动。
 * 在fling过程中再次滑动，记为同一次滑动。
 */
class ObservableScrollView : NestedScrollView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs, defStyleAttr)

    companion object {
        const val DELAY_TIME = 100L
    }

    //是否按下
    private var mInTouch = false
    //是否在滚动
    private var isScrolling = false
    //是否滚动过，用来区分单击和滚动
    private var scrolled = false
    //滑动停止监听
    private var listener: (() -> Unit)? = null

    //滚动监听倒计时器，计时${DELAY_TIME}毫秒后无滑动则为停止
    private val scrollCountTimer: CountDownTimer = object : CountDownTimer(DELAY_TIME, 1) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            isScrolling = false
            if (!mInTouch) {
                invokeListener()
            }
        }
    }

    init {
        setOnScrollChangeListener(
            OnScrollChangeListener { _, _, _, _, _ ->
                //重置滑动计时器、滑动状态
                scrollCountTimer.cancel()
                isScrolling = true
                scrolled = true
                scrollCountTimer.start()
            })
    }

    /**
     * 按下时置为滑动状态，抬起时置为非滑动状态
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val result = super.onTouchEvent(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> mInTouch = true
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mInTouch = false
                if (!isScrolling && scrolled) {
                    invokeListener()
                }
            }
            else -> { }
        }
        return result
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scrollCountTimer.cancel()
    }

    /**
     * 设置停止滚动监听
     */
    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    private fun invokeListener() {
        scrolled = false
        listener?.invoke()
    }
}