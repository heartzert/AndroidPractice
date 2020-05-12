package heartzert.test.all.custom_views.recyclertest.myRecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import heartzert.test.all.R
import heartzert.test.all.common.RecyclerviewAdapter
import heartzert.test.all.databinding.ActivityNestedTestBinding
import heartzert.test.all.databinding.ItemInsideRecyclerviewBinding

class NestedTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityNestedTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nested_test)
        binding.viewModel = this
        binding.recyclerView.adapter = MyParentRecyclerviewAdapter()

        binding.rootView.setRootList(binding.recyclerView)
    }

    inner class MyParentRecyclerviewAdapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = if (viewType == 1) {

                LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
            } else {
                val mbinding = DataBindingUtil.inflate<ItemInsideRecyclerviewBinding>(LayoutInflater.from(parent.context), R.layout.item_inside_recyclerview, parent, false)
                mbinding.recyclerView.adapter = RecyclerviewAdapter(40)
                binding.rootView.setChildList(mbinding.recyclerView)

                mbinding.root
            }
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 40
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == itemCount - 15) {
                2
            } else {
                1
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val textView = holder.itemView?.findViewById<TextView?>(R.id.textView)
            textView?.text = "lililililieheihei" + position
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}

