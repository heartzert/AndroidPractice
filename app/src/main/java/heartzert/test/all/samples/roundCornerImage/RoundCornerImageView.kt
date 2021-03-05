package heartzert.test.all.samples.roundCornerImage

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import heartzert.test.all.R

/**
 * Created by heartzert on 3/5/21.
 * Email: heartzert@163.com
 */
/**
 * 直接给这个view设置带圆角的背景即可实现圆角
 */
class RoundCornerImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        clipToOutline = true
        setBackgroundResource(R.drawable.bg_round_corner_image_view)
    }
}