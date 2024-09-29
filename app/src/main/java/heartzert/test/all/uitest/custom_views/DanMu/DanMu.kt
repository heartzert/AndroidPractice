package heartzert.test.all.uitest.custom_views.DanMu

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * Created by heartzert on 2021/10/19.
 * Email: heartzert@163.com
 */

/**
 * 1. 先画出一个弹幕
 * 2. 画出好多个弹幕
 * 3. 给弹幕添加动画
 * 4. 抽象出弹幕ui
 * 5. 提供对外方法
 *
 * 待完成
 */
class DanMu: ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}