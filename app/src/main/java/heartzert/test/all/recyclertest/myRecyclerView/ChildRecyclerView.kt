package heartzert.test.all.recyclertest.myRecyclerView

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.NestedScrollingChild2
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2019/10/22.
 * Email: heartzert@163.com
 */
class ChildRecyclerView : RecyclerView, NestedScrollingChild2{

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,
        defStyle)


}