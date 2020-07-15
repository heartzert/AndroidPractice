package heartzert.test.all.android_conponents_paging.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import heartzert.test.all.R
import heartzert.test.all.android_conponents_paging.adaptor.MyAdaptor
import heartzert.test.all.android_conponents_paging.http.RedditApi
import heartzert.test.all.android_conponents_paging.http.RedditApi.RedditPost
import heartzert.test.all.android_conponents_paging.repository.InMemoryByPageKeyRepository
import heartzert.test.all.databinding.ActivityViewModelViewBinding
import java.util.concurrent.Executors

class ViewModelActivity : AppCompatActivity() {

    var viewModel: ViewModelActivityVM? = null
    var binding: ActivityViewModelViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定viewModel到DataBinding布局
        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val executor = Executors.newFixedThreadPool(5)
                val redditApi = RedditApi.create()
                @Suppress("UNCHECKED_CAST")
                return ViewModelActivityVM(
                    savedInstanceState, InMemoryByPageKeyRepository(redditApi, executor)) as T
            }
        }).get(ViewModelActivityVM::class.java)
        binding =
            DataBindingUtil.setContentView<ActivityViewModelViewBinding>(this, R.layout.activity_view_model)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initRecyclerView()
        initSwipeToRefresh()

        viewModel?.showSubreddit("test")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun initRecyclerView() {
        val adapter = MyAdaptor { viewModel?.retry() }
        viewModel?.posts?.observe(this, Observer<PagedList<RedditPost>> {
            adapter.submitList(it)
        })
        viewModel?.networkState?.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {

    }
}
