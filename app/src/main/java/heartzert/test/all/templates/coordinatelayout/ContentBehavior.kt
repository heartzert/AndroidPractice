package heartzert.test.all.templates.coordinatelayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior

/**
 * Created by heartzert on 2019/9/16.
 * Email: heartzert@163.com
 */
class ContentBehavior : Behavior<View> {

    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

//    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
//        if (dependency.)
//            return super.layoutDependsOn(parent, child, dependency)
//    }

//    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
//        val params = child.layoutParams as? CoordinatorLayout.LayoutParams
//        if (params != null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
//            child.layout(0, 0, parent.width, parent.height)
//            child.translationY = 200f
//            return true
//        }
//        return super.onLayoutChild(parent, child, layoutDirection)
//    }

}