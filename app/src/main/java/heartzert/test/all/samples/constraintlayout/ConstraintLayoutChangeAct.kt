package heartzert.test.all.samples.constraintlayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionManager
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityConstraintLayoutChangeBinding

class ConstraintLayoutChangeAct : AppCompatActivity() {
    private var storeConstraintSet: ConstraintSet? = null

    private lateinit var binding : ActivityConstraintLayoutChangeBinding

    companion object {
        var times = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_constraint_layout_change)
    }

    fun test(view: View) {
        if (storeConstraintSet == null) {
            storeConstraintSet = ConstraintSet().apply { clone(binding.fullLayout) }
        }
        TransitionManager.beginDelayedTransition(binding.fullLayout)
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
        TransitionManager.beginDelayedTransition(binding.fullLayout)
        storeConstraintSet?.applyTo(binding.fullLayout)
    }

    private fun test1() {
        ConstraintSet()
            .apply { clone(binding.fullLayout) }
            .apply { setMargin(binding.button1.id, ConstraintSet.START, 100) }
            .apply { connect(binding.button1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 1000) }
            .applyTo(binding.fullLayout)
    }

    private fun test2() {
        ConstraintSet()
            .apply { clone(binding.fullLayout) }
            .apply { clearConstraint(this, binding.button1, true) }
            .apply { clearConstraint(this, binding.button2, true) }
            .apply { clearConstraint(this, binding.button3, true) }
            .apply {
                //这里的参数不能为Constraint.Start/ConstraintSet.End否则会报错
                createHorizontalChain(
                    ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                    intArrayOf(binding.button1.id, binding.button2.id, binding.button3.id),
                    null,
                    ConstraintSet.CHAIN_PACKED
                )
            }
            .applyTo(binding.fullLayout)
    }

    private fun test3() {
        ConstraintSet()
            .apply {
                clone(binding.fullLayout)
                clearConstraint(this, binding.button1)
                clearConstraint(this, binding.button2)
                clearConstraint(this, binding.button3)
                setMargin0(this, binding.button1.id)
                setMargin0(this, binding.button2.id)
                setMargin0(this, binding.button3.id)
                connect(binding.button1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                connect(binding.button1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                connect(binding.button1.id, ConstraintSet.END, binding.button2.id, ConstraintSet.START)
                connect(binding.button2.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                connect(binding.button2.id, ConstraintSet.START, binding.button1.id, ConstraintSet.END)
                connect(binding.button2.id, ConstraintSet.END, binding.button3.id, ConstraintSet.START)
                connect(binding.button3.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                connect(binding.button3.id, ConstraintSet.START, binding.button2.id, ConstraintSet.END)
                connect(binding.button3.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            }
            .applyTo(binding.fullLayout)
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
