package heartzert.test.all.uitest.bubbles

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.*
import android.widget.FrameLayout
import heartzert.lib.utils.dp

/**
 * Created by heartzert on 2/20/21.
 * Email: heartzert@163.com
 */
class BubblesLayout : FrameLayout {

    companion object {
        private const val BUBBLES_PER_CLICK = 20

        private const val ANIMATION_DURATION = 500L

        private val BUBBLES_ORIGIN_RADIUS = 40.dp()

        private val TRANSLATION_DISTANCE = 150.dp()
    }

    private val colorList =
        setOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW)


    private val scaleInterpolatorList = setOf(
        DecelerateInterpolator(),
        AccelerateInterpolator(),
        AccelerateDecelerateInterpolator(),
        AnticipateInterpolator(),
        AnticipateOvershootInterpolator()
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            repeat(BUBBLES_PER_CLICK) {
                val bubble = createBubble(event.x.toInt(), event.y.toInt())
                addView(bubble)
                val animSet = AnimationSet(false).apply {
                    addAnimation(createScalAnimation())
                    addAnimation(createTranslateAnimation())
                }
                bubble.startAnimation(animSet.apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            handler.post {
                                removeView(bubble)
                            }
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                    })
                })
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun createBubble(x: Int, y: Int) = CircleView(context).apply {
        layoutParams = LayoutParams(BUBBLES_ORIGIN_RADIUS, BUBBLES_ORIGIN_RADIUS).apply {
            setColor(colorList.random())
            setMargins(x, y, 0, 0)
        }
    }

    private fun createScalAnimation(): ScaleAnimation {
        return ScaleAnimation(
            1f,
            0f,
            1f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = ANIMATION_DURATION
            interpolator = scaleInterpolatorList.random()
        }
    }

    private fun createTranslateAnimation(): TranslateAnimation {
        return TranslateAnimation(
            0f,
            (-TRANSLATION_DISTANCE / 2..TRANSLATION_DISTANCE / 2).random().toFloat(),
            0f,
            (-TRANSLATION_DISTANCE / 2..TRANSLATION_DISTANCE / 2).random().toFloat()
        )
            .apply {
                duration = ANIMATION_DURATION
                interpolator = scaleInterpolatorList.random()
            }
    }
}