package heartzert.test.all.recyclertest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import me.tatarka.bindingcollectionadapter2.BindingListViewAdapter
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter

/**
 * Created by heartzert on 2019/10/21.
 * Email: heartzert@163.com
 */
open class LogRecyclerViewAdapter<T>: BindingRecyclerViewAdapter<T>() {

    override fun onCreateViewHolder(binding: ViewDataBinding): ViewHolder {
        Log.d("========", "onCreateViewHolder")
        return super.onCreateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        Log.d("========", "onBindViewHolder")
        super.onBindViewHolder(holder, position, payloads)
    }

}