package heartzert.test.all.uitest

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created by heartzert on 2019/8/10.
 * Email: heartzert@163.com
 */

@BindingAdapter("fakeBold")
fun setFakeBold(view: TextView, int: Int) {
    view.text = SpannableString(view.text).apply {
        view.paint.isFakeBoldText = true
//        view.paint.strokeWidth = 100f
    }
}

@BindingAdapter("isBold")
fun setBold(view: TextView?, isBold: Boolean?) {
    if (isBold == true) {
        view?.setTypeface(null, Typeface.BOLD)
    } else {
        view?.setTypeface(null, Typeface.NORMAL)
    }
}