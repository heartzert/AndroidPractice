package heartzert.test.all.kotlinSamples

import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by heartzert on 8/6/21.
 * Email: heartzert@163.com
 */

//kotlin timeout 的测试代码
fun testTimeout() {
    MainScope().launch {
        //这两个timeout是串行的，如果要并行，用sync包裹即可
        val time = System.currentTimeMillis()
        Log.d("==========wxz", "time1: 0")
        val a = withTimeoutOrNull(10000) {
            delay(5000)
            true
        }
        Log.d("==========wxz", "time2: ${System.currentTimeMillis() - time}")
        val b = withTimeoutOrNull(10000) {
            delay(5000)
            true
        }
        Log.d("==========wxz", "time3: ${System.currentTimeMillis() - time}")
        Log.d("==========wxz", "a = $a, b = $b}")
    }

    //并行的例子
    MainScope().launch {
        val time = System.currentTimeMillis()
        Log.d("==========wxz", "time1: 0")
        val a =
            async {
                withTimeoutOrNull(10000) {
                    delay(5000)
                    true
                }
            }
        Log.d("==========wxz", "time2: ${System.currentTimeMillis() - time}")
        val b =
            async {
                withTimeoutOrNull(10000) {
                    delay(5000)
                    true
                }
            }
        Log.d("==========wxz", "time3: ${System.currentTimeMillis() - time}")
        Log.d("==========wxz", "a = ${a.await()}, b = ${b.await()}}")
        Log.d("==========wxz", "time4: ${System.currentTimeMillis() - time}")
    }
}

