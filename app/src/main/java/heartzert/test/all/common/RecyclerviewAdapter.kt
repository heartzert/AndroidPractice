package heartzert.test.all.common

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import heartzert.test.all.R

/**
 * Created by heartzert on 2020/1/3.
 * Email: heartzert@163.com
 */

class RecyclerviewAdapter(val itemNumber: Int = 20) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("========", "onCreateViewHolder => ")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_high, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemNumber
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("========", "onBindViewHolder => holder=$holder, position=$position")
        val textView = holder.itemView?.findViewById<TextView>(R.id.textView)
        textView.text = "aahahahhehieheihei" + position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}