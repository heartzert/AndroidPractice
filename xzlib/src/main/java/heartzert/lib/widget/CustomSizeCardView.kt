package heartzert.lib.widget

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

/**
 * Created by heartzert on 2020/12/15.
 * Email: heartzert@163.com
 */
/**
 * 在使用Recyclerview的瀑布流布局时，要在onBindView时，设置好该view的宽高，不然上下滑动时会出现跳跃。
 * 要重写item的root view的onMeasure方法，设置好宽高
 * 这个类不是很通用
 */
class CustomSizeCardView : CardView {

  var mRate = 0.0

  constructor(context: Context) : super(context)
  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs)

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr)

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    if (mRate > 0) {
      val mMeasuredHeight = (mRate * MeasureSpec.getSize(widthMeasureSpec)).toInt()
      val mHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMeasuredHeight, MeasureSpec.EXACTLY)
      super.onMeasure(widthMeasureSpec, mHeightMeasureSpec)
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }
}