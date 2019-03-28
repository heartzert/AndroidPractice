package heartzert.test.all

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import heartzert.test.all.constraintlayout.ConstraintLayoutChangeAct
import heartzert.test.all.coordinatelayout.CoordinateLayoutAct
import heartzert.test.all.nestedscrollview.NestedScrollViewAct

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun nestedScrollView(view: View) {
        startActivity(Intent(this, NestedScrollViewAct::class.java))
    }

    fun coordinateLayout(view: View) {
        startActivity(Intent(this, CoordinateLayoutAct::class.java))
    }

    fun test(view: View) {
        startActivity(Intent(this, ConstraintLayoutChangeAct::class.java))
//        startActivity(Intent(this, AsyncTaskTestAct::class.java))
    }
}
