package heartzert.test.all.samples.viewpageranim.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import heartzert.test.all.R
import heartzert.test.all.databinding.CardFragmentViewBinding

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */
class CardFragment : Fragment() {

    private var viewBinding: CardFragmentViewBinding? = null

    val text = ObservableField<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding =
            DataBindingUtil.inflate<CardFragmentViewBinding>(inflater, R.layout.fragment_card, container, false)
        viewBinding?.viewModel = this
        return viewBinding?.root
    }

    fun setText(string: String) {
        text.set(string)
    }

    fun click() {
        text.set((text.get()?.toInt() ?: 0 + 1).toString())
        Log.d("======", "click click ${text.get()}")
    }

    fun cancel() {
        Toast.makeText(context, "click cancel ${text.get()}", Toast.LENGTH_LONG).show()
    }

    fun textClick() {
        Toast.makeText(context, "click text ${text.get()}", Toast.LENGTH_LONG).show()
    }
}