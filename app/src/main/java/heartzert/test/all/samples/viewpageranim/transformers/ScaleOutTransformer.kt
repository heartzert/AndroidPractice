package heartzert.test.all.samples.viewpageranim.transformers

import android.view.View
import heartzert.lib.utils.unAbs
import kotlin.math.abs

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */
class ScaleOutTransformer : ITransformer {

    companion object {
        const val ALPHA_ITEM = 1f / 4
    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            pivotX = width / 2f
            pivotY = height / 2f
            when {
                position <= -1f -> {

                }

                position <= 0f -> {
                    translationX = unAbs(width) * position
                    alpha = 1f + position
                    scaleX = abs(position) + 1
                    scaleY = abs(position) + 1
                }

                else -> {
                    translationX = unAbs(width) * position
                }
            }
        }
    }
}