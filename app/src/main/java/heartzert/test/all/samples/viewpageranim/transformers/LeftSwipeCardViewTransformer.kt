package heartzert.test.all.samples.viewpageranim.transformers

import android.view.View
import heartzert.lib.utils.unAbs

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */

class LeftSwipeCardViewTransformer : ITransformer {

    companion object {
        const val TRANS_XY = 10f
        const val ALPHA_ITEM = 1f / 4
    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position <= -0.2f -> {
                    translationX = 0f
                    translationY = 0f
                    alpha = (1f + position) * 1.25f
//                    isClickable = false
                }
                position <= 0f -> {
                    translationX = 0f
                    translationY = 0f
                    alpha = 1f
                    if (position == 0f) {
//                        isClickable = true
                    }
                }
                position <= 0.8f -> {
                    translationX = (width.unAbs() + TRANS_XY) * position
                    translationY = (0 - TRANS_XY) * position
                    alpha = 1f
//                    isClickable = false
//                    pivotX = width / 2f
//                    pivotY = height / 2f
//                    scaleX = Math.pow(0.9, position.toDouble()).toFloat()
//                    scaleY = Math.pow(0.9, position.toDouble()).toFloat()
                }
                else -> {
//                    isClickable = false
                    translationX = (width.unAbs() + TRANS_XY) * position
                    translationY = (0 - TRANS_XY) * position
                    alpha = 1f - ALPHA_ITEM * position
                }
            }
        }

    }
}
