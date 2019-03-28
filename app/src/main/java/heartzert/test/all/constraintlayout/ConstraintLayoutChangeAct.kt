package heartzert.test.all.constraintlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.util.Log
import android.view.View
import heartzert.test.all.R
import kotlinx.android.synthetic.main.activity_constraint_layout_change.button1
import kotlinx.android.synthetic.main.activity_constraint_layout_change.button2
import kotlinx.android.synthetic.main.activity_constraint_layout_change.button3
import kotlinx.android.synthetic.main.activity_constraint_layout_change.full_layout

class ConstraintLayoutChangeAct : AppCompatActivity() {
    private var storeConstraintSet: ConstraintSet? = null

    companion object {
        var times = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout_change)
    }

    fun test(view: View) {
        if (storeConstraintSet == null) {
            storeConstraintSet = ConstraintSet().apply { clone(full_layout) }
        }
        Log.d("======", "dianle test")
        TransitionManager.beginDelayedTransition(full_layout)
        when (times) {
            0 -> {
                test1();times++
            }
            1 -> {
                test2();times++
            }
            2 -> {
                test3();times++
            }
            else -> {
                reset(view);times = 0
            }
        }
    }

    fun reset(view: View) {
        Log.d("======", "dianle reset")
        TransitionManager.beginDelayedTransition(full_layout)
        storeConstraintSet?.applyTo(full_layout)
    }

    private fun test1() {
        ConstraintSet()
            .apply { clone(full_layout) }
            .apply { setMargin(button1.id, ConstraintSet.START, 100) }
            .apply { connect(button1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 1000) }
            .applyTo(full_layout)
    }

    private fun test2() {
        ConstraintSet()
            .apply { clone(full_layout) }
            .apply { clearConstraint(this, button1, true) }
            .apply { clearConstraint(this, button2, true) }
            .apply { clearConstraint(this, button3, true) }
            .apply {
                //这里的参数不能为Constraint.Start/ConstraintSet.End否则会报错
                createHorizontalChain(
                    ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                    intArrayOf(button1.id, button2.id, button3.id),
                    null,
                    ConstraintSet.CHAIN_PACKED
                )
            }
            .applyTo(full_layout)
    }

    private fun test3() {
        ConstraintSet()
            .apply { clone(full_layout) }
            .apply { clearConstraint(this, button1) }
            .apply { clearConstraint(this, button2) }
            .apply { clearConstraint(this, button3) }
            .apply { setMargin0(this, button1.id) }
            .apply { setMargin0(this, button2.id) }
            .apply { setMargin0(this, button3.id) }
            .apply { connect(button1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP) }
            .apply { connect(button1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START) }
            .apply { connect(button1.id, ConstraintSet.END, button2.id, ConstraintSet.START) }
            .apply { connect(button2.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP) }
            .apply { connect(button2.id, ConstraintSet.START, button1.id, ConstraintSet.END) }
            .apply { connect(button2.id, ConstraintSet.END, button3.id, ConstraintSet.START) }
            .apply { connect(button3.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP) }
            .apply { connect(button3.id, ConstraintSet.START, button2.id, ConstraintSet.END) }
            .apply { connect(button3.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END) }
            .applyTo(full_layout)
    }
}

fun clearConstraint(constraintSet: ConstraintSet, view: View, andMargin: Boolean = false): ConstraintSet {
    val width = view.width
    val height = view.height
    return constraintSet.apply { clear(view.id) }
        .apply { if (andMargin) setMargin0(constraintSet, view.id) }
        .apply { constrainHeight(view.id, height) }
        .apply { constrainWidth(view.id, width) }
}

fun setMargin0(constraintSet: ConstraintSet, viewId: Int): ConstraintSet {
    return constraintSet
        .apply { setMargin(viewId, ConstraintSet.START, 0) }
        .apply { setMargin(viewId, ConstraintSet.TOP, 0) }
        .apply { setMargin(viewId, ConstraintSet.END, 0) }
        .apply { setMargin(viewId, ConstraintSet.BOTTOM, 0) }
}
