package heartzert.test.all.android_conponents_paging.ui

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import heartzert.test.all.android_conponents_paging.repository.RedditPostRepository

/**
 * Created by heartzert on 2019/11/6.
 * Email: heartzert@163.com
 */
class ViewModelActivityVM(savedInstance: Bundle?, repository: RedditPostRepository) : ViewModel() {

    private val subredditName = MutableLiveData<String>()
    private val repoResult = Transformations.map(subredditName) {
        repository.postsOfSubreddit(it, 30)
    }
    val posts = Transformations.switchMap(repoResult) { it.pagedList }
    val networkState = Transformations.switchMap(repoResult) { it.networkState }
    val refreshState = Transformations.switchMap(repoResult) { it.refreshState }

    companion object {
        private const val SAVE_INFO_NUMBER = "InfoNumber"
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

    fun showSubreddit(subreddit: String): Boolean {
        if (subredditName.value == subreddit) {
            return false
        }
        subredditName.value = subreddit
        return true
    }

    val text = MutableLiveData<String>("你好")

    val num = MutableLiveData<Int>(0)

    init {
        if (savedInstance != null) {
            num.value = savedInstance.getInt(
                SAVE_INFO_NUMBER)
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            SAVE_INFO_NUMBER, num.value!!)
    }

    fun onButtonClick() {
        num.value = num.value?.plus(1)
    }
}