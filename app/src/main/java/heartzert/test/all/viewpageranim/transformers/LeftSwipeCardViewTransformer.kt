package heartzert.test.all.viewpageranim.transformers

import android.support.v4.view.ViewPager.PageTransformer
import android.view.View
import heartzert.lib.unAbs

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */

class LeftSwipeCardViewTransformer : PageTransformer {

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
                }
                position <= 0f -> {
                    translationX = 0f
                    translationY = 0f
                    alpha = 1f
                }
                position <= 0.8f -> {
                    translationX = (unAbs(width) + TRANS_XY) * position
                    translationY = (0 - TRANS_XY) * position
                    alpha = 1f
//                    pivotX = width / 2f
//                    pivotY = height / 2f
//                    scaleX = Math.pow(0.9, position.toDouble()).toFloat()
//                    scaleY = Math.pow(0.9, position.toDouble()).toFloat()
                }
                else -> {
                    translationX = (unAbs(width) + TRANS_XY) * position
                    translationY = (0 - TRANS_XY) * position
                    alpha = 1f - ALPHA_ITEM * position
                }
            }
        }

    }
}
