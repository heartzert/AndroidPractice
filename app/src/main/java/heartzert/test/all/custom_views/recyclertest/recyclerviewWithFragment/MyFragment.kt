package heartzert.test.all.custom_views.recyclertest.recyclerviewWithFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import heartzert.test.all.R

/**
 * Created by heartzert on 2019/10/23.
 * Email: heartzert@163.com
 */

class MyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_in_recycler, container, false)
        view.findViewById<RecyclerView>(R.id.recyclerView).adapter = MyFragmentAdapter()
        return view
    }

    class MyFragmentAdapter: RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}