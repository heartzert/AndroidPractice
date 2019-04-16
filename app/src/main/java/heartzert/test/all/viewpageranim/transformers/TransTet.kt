package heartzert.test.all.viewpageranim.transformers

import android.view.View
import android.widget.TextView
import heartzert.lib.dp2px
import heartzert.lib.unAbs
import heartzert.test.all.R

/**
 * Created by heartzert on 2019/4/16.
 * Email: heartzert@163.com
 */
class TransTet : ITransformer {

    companion object {
        var MAX = 2
        var TRANS_Y = dp2px(12f)
        var TRANS_X = dp2px(21f)
        var ALPHA_ITEM = 1f / MAX
    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val textView = view.findViewById<TextView>(R.id.card_text)
            when {
                position <= -0.2f -> {
                    translationX = 0f
                    translationY = 0f
                    alpha = (1f + position) * 1.25f
                }
                position <= 0f -> {
                    translationX = 0f
                    translationY = 0f
                    alpha = 1f
                }
                else -> {
                    translationX = (unAbs(width) + TRANS_X) * position
                    translationY = (0 - TRANS_Y) * position
                    alpha = ((1f - ALPHA_ITEM * position) * 1.5).toFloat()
                }
            }
        }

    }
}