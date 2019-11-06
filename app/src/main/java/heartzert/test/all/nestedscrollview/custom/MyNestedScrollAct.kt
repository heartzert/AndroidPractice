package heartzert.test.all.nestedscrollview.custom

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import heartzert.test.all.R
import kotlinx.android.synthetic.main.activity_my_nested_scroll.recyclerView

class MyNestedScrollAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_nested_scroll)
        recyclerView.adapter = Adapter()
    }

    class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = TextView(parent.context).apply { text = "hahaahah" }
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 100
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 99) {
                2
            } else {
                1
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
