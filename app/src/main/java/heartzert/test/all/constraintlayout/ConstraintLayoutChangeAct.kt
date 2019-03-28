package heartzert.test.all.constraintlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.util.Log
import android.view.View
import heartzert.test.all.R
import kotlinx.android.synthetic.main.activity_constraint_layout_change.button1
import kotlinx.android.synthetic.main.activity_constraint_layout_change.full_layout

class ConstraintLayoutChangeAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_change)
    }

    fun test(view: View) {
        Log.d("======", "dianle")
        TransitionManager.beginDelayedTransition(full_layout)
        test1()
    }

    private fun test1() {
        ConstraintSet()
            .apply { clone(full_layout) }
            .apply { setMargin(button1.id, ConstraintSet.START, 100) }
            .apply { connect(button1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 1000) }
            .applyTo(full_layout)
    }

    private fun test2() {

    }
}
