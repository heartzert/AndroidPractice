package heartzert.test.all.kotlinSamples

import heartzert.test.all.common.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

/**
 * Created by heartzert on 8/31/21.
 * Email: heartzert@163.com
 */
class CoroutineTest {
}

suspend fun main() {
    log(1)
    suspendCoroutine<String> {
    }
}