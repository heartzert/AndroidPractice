package heartzert.lib.utils

import android.content.res.ColorStateList
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

/**
 * Created by heartzert on 2020/3/9.
 * Email: heartzert@163.com
 */

/**
 * 控制是否显示view
 *
 * @param v
 * @param visible 是否显示view，true为VISIBLE，false为GONE，没有考虑INVISIBLE的情况
 */
@BindingAdapter(value = ["app:visible", "app:withAnim"], requireAll = false)
fun setVisible(v: View?, visible: Boolean, withAnim: Boolean = false) {
    if (withAnim) {
        val anim: Animation?
        if (visible) {
            anim = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
            anim.duration = 500
        } else {
            anim = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f)
            anim.duration = 500
        }
        v?.startAnimation(anim)
    }
    v?.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * 控制是否显示view
 *
 * @param v
 * @param visible 是否显示view，true为VISIBLE，false为INVISIBLE
 */
@BindingAdapter("app:hide")
fun setHide(v: View?, visible: Boolean) {
    v?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * constraintLayout 的 layout_constraintHorizontal_bias 属性的 BindingAdapter
 *
 * @param view
 * @param float Bia的值， 取值范围0-1，0为靠左，1为靠右，0.5中间
 */
@BindingAdapter("app:layout_constraintHorizontal_bias")
fun setConstraintHorizontalBias(view: View, float: Float) {
    val params = view.layoutParams as ConstraintLayout.LayoutParams
    params.horizontalBias = float
    view.layoutParams = params
}

@BindingAdapter("app:tint")
fun setTint(imageView: ImageView?, int: Int) {
    if (int == 0 || int == -1) {
        ImageViewCompat.setImageTintList(imageView ?: return, null)
    } else {
        ImageViewCompat.setImageTintList(imageView ?: return, ColorStateList.valueOf(int))
    }
}