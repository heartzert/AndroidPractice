package heartzert.test.all.android_conponents_paging.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import heartzert.test.all.R
import heartzert.test.all.android_conponents_paging.repository.NetworkState
import heartzert.test.all.android_conponents_paging.repository.Status.FAILED
import heartzert.test.all.android_conponents_paging.repository.Status.RUNNING
import heartzert.test.all.databinding.NetworkStateItemBinding

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(val binding: NetworkStateItemBinding,
                                 private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState?) {
        binding.progressBar.visibility = toVisibility(networkState?.status == RUNNING)
        binding.retryButton.visibility = toVisibility(networkState?.status == FAILED)
        binding.errorMsg.visibility = toVisibility(networkState?.msg != null)
        binding.errorMsg.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val binding = DataBindingUtil.inflate<NetworkStateItemBinding>(LayoutInflater.from(parent.context),
                R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(binding, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
