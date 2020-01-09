package heartzert.test.all.uitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import heartzert.test.all.BR
import heartzert.test.all.R
import heartzert.test.all.common.RecyclerviewAdapter
import heartzert.test.all.databinding.ScrollRecyclerBinding
import me.tatarka.bindingcollectionadapter2.itemBindingOf

class ScrollRecyclerActivity : AppCompatActivity() {

    val itemBinding = itemBindingOf<RecyclerItem>(BR.item, R.layout.item_recycler)

    val items = ObservableArrayList<RecyclerItem>().apply {
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
        add(RecyclerItem())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ScrollRecyclerBinding>(this, R.layout.activity_scroll_recycler)
        binding.setVariable(BR.viewModel, this)
        binding.recyclerView.adapter = RecyclerviewAdapter()
    }
}
