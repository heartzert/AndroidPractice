package heartzert.test.all.common

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Created by heartzert on 2020/1/3.
 * Email: heartzert@163.com
 */

class ParentRecyclerviewAdapter : RecyclerView.Adapter<ViewHolder>() {
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