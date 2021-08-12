package heartzert.test.all.kotlinSamples

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by heartzert on 8/3/21.
 * Email: heartzert@163.com
 */

/**
 * java回调转kotlin协程的例子
 */
class CallBackToSuspendTest {

    interface TestCallback {
        fun success(s: String)
        fun failed()
    }

    var mCallback: TestCallback? = null

    private fun testCallback(callback: TestCallback) {
        //模拟外部的有回调的方法
    }

    @InternalCoroutinesApi
    private suspend fun suspendRequestSub(): String {
        return suspendCancellableCoroutine {
            //协程scope取消的时候会执行这一段
            it.invokeOnCancellation {

            }
            //
            testCallback(object : TestCallback {
                override fun success(s: String) {
                    it.tryResume(s)
                    //上下等价
                    try {
                        it.resume(s)
                    } catch (ignore: Exception) {

                    }
                }

                override fun failed() {
                    it.tryResumeWithException(Exception())
                    //上下等价
                    try {
                        it.resumeWithException(Exception())
                    } catch (ignore: Exception) {
                        //
                    }
                }

            })
        }
    }

}