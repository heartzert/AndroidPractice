package heartzert.test.algrithom.kotlin.singleton

/**
 * Created by heartzert on 2020/7/31.
 * Email: heartzert@163.com
 */
//双重检测单例，kotlin版
class DoubleCheckSingleton private constructor() {

    companion object {
        @Volatile
        private var instance: DoubleCheckSingleton? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DoubleCheckSingleton().also { instance = it }
        }
    }
}