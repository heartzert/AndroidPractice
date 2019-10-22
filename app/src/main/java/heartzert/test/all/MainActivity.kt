package heartzert.test.all

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.coordinatelayout.CoordinateLayoutAct
import heartzert.test.all.coordinatelayout.CoordinateTest2Activity
import heartzert.test.all.customview.ViewTestAct
import heartzert.test.all.nestedscrollview.NestedScrollViewAct
import heartzert.test.all.recyclertest.RecyclerViewTestActivity
import heartzert.test.all.uitest.ScrollRecyclerActivity
import heartzert.test.all.uitest.TextSizeTestActivity
import heartzert.test.all.uitest.UITestAct
import heartzert.test.all.viewpager_fragment.ViewpagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun nestedScrollView(view: View) {
//        startActivity(Intent(this, NestedScrollViewAct::class.java))
//        startActivity(Intent(this, CoordinateTest2Activity::class.java))
        startActivity(Intent(this, RecyclerViewTestActivity::class.java))
    }

    fun coordinateLayout(view: View) {
        startActivity(Intent(this, ViewTestAct::class.java))
    }

    fun test(view: View) {
//        startActivity(Intent(this, ConstraintLayoutChangeAct::class.java))
//        startActivity(Intent(this, UITestAct::class.java))
//        startActivity(Intent(this, TextSizeTestActivity::class.java))
//        startActivity(Intent(this, ViewpagerActivity::class.java))
        startActivity(Intent(this, ShadowActActivity::class.java))
//        startActivity(Intent(this, TestLSCVAct::class.java))
//        startActivity(Intent(this, UITestAct::class.java))
//        startActivity(Intent(this, AsyncTaskTestAct::class.java))
    }
}
