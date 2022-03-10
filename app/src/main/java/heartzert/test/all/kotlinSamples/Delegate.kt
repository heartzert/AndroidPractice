package heartzert.test.all.kotlinSamples

import android.util.Log

/**
 * Created by heartzert on 2022/1/13.
 * Email: heartzert@163.com
 *
 * 利用委托机制实现多继承案例
 */
interface A {
    fun funcA()
}

class AImplementation: A {
    override fun funcA() {
        Log.d("==========wxz", "im func a")
    }
}

open class Father {
    open fun funcFather() {
        Log.d("==========wxz", "im func father")
    }
}

class Son : Father(), A by AImplementation() {
    override fun funcA() {
        Log.d("==========wxz", "im new func a")
    }

    override fun funcFather() {
        super.funcFather()
        Log.d("==========wxz", "im new func father")
    }
}