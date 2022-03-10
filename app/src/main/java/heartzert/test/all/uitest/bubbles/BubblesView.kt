package heartzert.test.all.uitest.bubbles

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.core.view.updateLayoutParams
import heartzert.lib.utils.dp

/**
 * Created by heartzert on 2/20/21.
 * Email: heartzert@163.com
 */
class BubblesView: View {

    companion object {
        const val BUBBLES_PER_CLICK = 10

        const val BUBBLES_RADIUS_DP = 10

        val colorList =
            listOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        setOnTouchListener { v, event ->

            return@setOnTouchListener false
        }
    }

    fun createBubble() = CircleView(context).apply {
        updateLayoutParams {
            val radius = (1..BUBBLES_RADIUS_DP).random().dp
            height = radius
            width = radius
            setColor(colorList[(colorList.indices).random()])
        }
    }
}