package heartzert.lib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by heartzert on 2024.05.06.
 * Email: heartzert@163.com
 */
class CommonAdapter<T> private constructor() : RecyclerView.Adapter<CommonAdapter<T>.CommonViewHolder>() {

    private var mDataList: List<T> = arrayListOf()
    private var mLayoutId: Int? = null
    private var onBindView: (viewHolder: CommonAdapter<T>.CommonViewHolder, position: Int, data: T?) -> Unit = { _, _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mLayoutId!!, parent, false)
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        onBindView.invoke(holder, position, mDataList.getOrNull(position))
    }

    inner class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class Builder<T> {

        private var commentAdapter: CommonAdapter<T> = CommonAdapter()

        fun setData(lists: List<T>): Builder<T> {
            commentAdapter.mDataList = lists
            return this
        }

        fun setLayoutId(layoutId: Int): Builder<T> {
            commentAdapter.mLayoutId = layoutId
            return this
        }

        fun bindView(bindView: (viewHolder: CommonAdapter<T>.CommonViewHolder, position: Int, data: T?) -> Unit): Builder<T> {
            commentAdapter.onBindView = bindView
            return this
        }

        fun create(): CommonAdapter<T> {
            return commentAdapter
        }
    }

}