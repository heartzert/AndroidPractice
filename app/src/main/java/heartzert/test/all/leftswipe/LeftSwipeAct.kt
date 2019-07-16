package heartzert.test.all.leftswipe

import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import heartzert.test.all.R

class LeftSwipeAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_swipe)
    }

    private fun addItemTouchEvent() {
        val mRecyclerView: RecyclerView? = null
        mRecyclerView?.addOnItemTouchListener(object : OnItemTouchListener {
            val gestureDetector = GestureDetector(this@LeftSwipeAct, object : SimpleOnGestureListener() {

                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    e1 ?: return false
                    e2 ?: return false
                    mRecyclerView ?: return false

                    val child = mRecyclerView?.findChildViewUnder(e1.x, e1.y)
                    child ?: return false

                    val deleteLayout: View? = null
                    try {
//                        deleteLayout = child.findViewById<View>(1)
                    } catch (e: Exception) {
                        return false
                    }

//                    if (true) {
//                        if (deleteLayout.visibility == View.VISIBLE) {
//                    TransitionManager.beginDelayedTransition(mRecyclerView)
//                            deleteLayout.visibility = View.GONE
//                        } else {
//                            TransitionManager.beginDelayedTransition(mRecyclerView)
                    deleteLayout?.visibility = View.VISIBLE
//                        }
                    return true
//                    }
//                    return false
                }

                override fun onLongPress(e: MotionEvent?) {
                }
            })

            override fun onTouchEvent(recyclerView: RecyclerView, event: MotionEvent) {
                gestureDetector.onTouchEvent(event)
            }

            override fun onInterceptTouchEvent(recyclerView: RecyclerView, event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {
            }
        })
    }
}
