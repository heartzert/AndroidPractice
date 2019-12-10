package heartzert.test.all.android_conponents_paging.repository

import heartzert.test.all.android_conponents_paging.repository.Status.FAILED
import heartzert.test.all.android_conponents_paging.repository.Status.RUNNING
import heartzert.test.all.android_conponents_paging.repository.Status.SUCCESS

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null
) {

    companion object {
        val LOADED = NetworkState(SUCCESS)
        val LOADING = NetworkState(RUNNING)
        fun error(msg: String?) = NetworkState(FAILED, msg)
    }

}