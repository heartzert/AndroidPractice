package heartzert.test.all.customview.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * Created by heartzert on 2019/12/18.
 * Email: heartzert@163.com
 */
class FlowLayout: ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        initMeasureParams()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun initMeasureParams() {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}