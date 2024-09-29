package heartzert.test.all.samples.lock

import java.util.*
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

/**
 * Created by heartzert on 2/18/21.
 * Email: heartzert@163.com
 *
 * 练习信号量
 *
 * 一个对象池的简单例子。构造函数为对象池大小以及用于初始化的对象
 */
class ObjPool<T, R>(poolSize: Int, t: T) {

    companion object {
        //信号量的初始值（可并行数）
        const val CONCURRENT_LIMIT = 3
    }

    private val s = Semaphore(CONCURRENT_LIMIT)

    //这里使用Vector而不是ArrayList的原因是：
    //信号量可能允许多个线程同时进入互斥区，所以内部操作还是有多线程风险。
    //区别于锁，同一时刻只能有一个线程进入互斥区，所以内部绝对单线程。
    //
    //由此可知，信号量初始值大于1时，主要起到限流的作用，内部仍需多线程判断
    private val objPool = Vector<T>(poolSize)

    init {
        repeat(poolSize) {
            objPool.add(t)
        }
    }

    //使用线程池的对象执行某方法
    fun exec(func: (t: T) -> R): R {
        var mT: T? = null
        try {
            s.acquire()
            mT = objPool.removeAt(0)
            return func.invoke(mT)
        } finally {
            mT?.let { objPool.add(it) }
            s.release()
        }
    }
}

fun main() {
    val objPool = ObjPool<Long, String>(10, 2)
    //重复创建15个线程
    repeat(15) {
        thread {
            objPool.exec { it2 ->
                println("$it2, $it")
                Thread.sleep(1000)
                it.toString()
            }
        }
    }
}