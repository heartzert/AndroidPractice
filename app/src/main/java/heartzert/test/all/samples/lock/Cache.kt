package heartzert.test.all.samples.lock

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Created by heartzert on 2/18/21.
 * Email: heartzert@163.com
 */
class Cache<K, V> {
    private val hashMap = HashMap<K, V>()

    private val rwl = ReentrantReadWriteLock()

    private val rl = rwl.readLock()

    private val wl = rwl.writeLock()

    fun get(key: K): V? {
        return null
    }

    fun put(key: K, value: V) {

    }
}