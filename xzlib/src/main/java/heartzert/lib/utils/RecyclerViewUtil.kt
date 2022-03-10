package heartzert.lib.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2020/12/14.
 * Email: heartzert@163.com
 */


/**
 * 设置RecyclerView的item decoration
 */
fun RecyclerView.setItemDecoration(left: Int, top: Int, right: Int, bottom: Int) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.top = top
            outRect.left = left
            outRect.right = right
            outRect.bottom = bottom
        }
    })
}