package heartzert.lib.utils

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View

/**
 * Created by heartzert on 2022/3/3.
 * Email: heartzert@163.com
 */
object ViewTouchExpend {

  /**
   * 扩展点击区域的范围
   *
   * @param view       需要扩展的元素，此元素必需要有父级元素
   * @param expendSize 需要扩展的尺寸（以sp为单位的）
   */
  fun expendTouchArea(view: View?, expendSize: Int) {
    if (view != null) {
      val parentView = view.parent as? View ?: return
      parentView.post {
        val rect = Rect()
        //如果太早执行本函数，会获取rect失败，因为此时UI界面尚未开始绘制，无法获得正确的坐标
        view.getHitRect(rect)
        rect.left -= expendSize
        rect.top -= expendSize
        rect.right += expendSize
        rect.bottom += expendSize
        parentView.touchDelegate = TouchDelegate(rect, view)
      }
    }
  }
}