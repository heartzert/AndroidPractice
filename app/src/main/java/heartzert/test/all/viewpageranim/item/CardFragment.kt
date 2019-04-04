package heartzert.test.all.viewpageranim.item

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import heartzert.test.all.R
import heartzert.test.all.databinding.CardFragmentViewBinding

/**
 * Created by heartzert on 2019/4/3.
 * Email: heartzert@163.com
 */
class CardFragment : Fragment() {

    val text = ObservableField<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = DataBindingUtil.inflate<CardFragmentViewBinding>(inflater, R.layout.fragment_card, container, false)
        v.viewModel = this
        return v.root
    }

    fun setText(string: String) {
        text.set(string)
    }

    fun click(){
        Toast.makeText(this.context, "点了${text.get()}", Toast.LENGTH_SHORT).show()
    }
}