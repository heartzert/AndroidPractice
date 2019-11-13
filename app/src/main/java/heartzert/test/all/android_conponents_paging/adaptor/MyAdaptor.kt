package heartzert.test.all.android_conponents_paging.adaptor

import android.os.Bundle
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import heartzert.test.all.android_conponents_paging.viewHolder.RedditPostViewHolder
import heartzert.test.all.R
import heartzert.test.all.android_conponents_paging.http.RedditApi.RedditPost
import heartzert.test.all.android_conponents_paging.repository.NetworkState
import heartzert.test.all.android_conponents_paging.viewHolder.NetworkStateItemViewHolder
import java.lang.IllegalArgumentException

/**
 * Created by heartzert on 2019/11/13.
 * Email: heartzert@163.com
 */
class MyAdaptor(private val retryCallback: () -> Unit) : PagedListAdapter<RedditPost, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.reddit_post_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.network_state_item -> {
                NetworkStateItemViewHolder.create(parent) {}
            }
            R.layout.reddit_post_item -> {
                RedditPostViewHolder.create(parent)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.reddit_post_item -> (holder as RedditPostViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isNotEmpty()) {
            (holder as RedditPostViewHolder).updateScore(payloads[position] as Bundle)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem.name == newItem.name
            }

            /**
             * 获取当某Item改变时的一个预加载对象，该对象会在onBindViewHolder时发给你，拿到改变对象时自己处理动画之类的。
             */
            override fun getChangePayload(oldItem: RedditPost, newItem: RedditPost): Any? {
                val diffParams = Bundle()
                if (oldItem.score != newItem.score) {
                    diffParams.putInt("score", newItem.score)
                }
                if (diffParams.size() == 0) return null
                return diffParams
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

}
