package heartzert.test.all.custom_views.recyclertest.recyclerviewWithFragment

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityRecyclerViewWithFragmentBinding
import kotlinx.android.synthetic.main.activity_scroll_recycler.recyclerView

class RecyclerViewWithFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        DataBindingUtil.setContentView<ActivityRecyclerViewWithFragmentBinding>(this, R.layout.activity_recycler_view_with_fragment)
        recyclerView.adapter = MyAdapter()
    }

    class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view : View = when (viewType) {
                1 -> {
                    LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_recycler, parent, false)
                }
                2 -> {
                    LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
                }
                else -> {
                    LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
                }
            }
            return MyViewHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 19) {
                1
            } else {
                2
            }
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}