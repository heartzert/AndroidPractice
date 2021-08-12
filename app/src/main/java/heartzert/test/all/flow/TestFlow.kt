package heartzert.test.all.flow

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import heartzert.test.all.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by heartzert on 6/1/21.
 * Email: heartzert@163.com
 */
class TestFlow : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_empty_view)

        lifecycleScope.launch {
            channelTest()
        }
    }

    fun test() {

        lifecycleScope.launch {

        }

        MainScope().launch {
            flowOf(1, 2, 3)
                .map {
                    it.toString()
                }
        }

        lifecycleScope.launchWhenCreated {

        }
    }


    private suspend fun channelTest() {
        val channel = Channel<Int>(Channel.RENDEZVOUS)

        val consumer = lifecycleScope.launch {
            while (true) {
                val value = channel.receive()
                println(value)
            }
        }

        val producer = lifecycleScope.launch {
            var i = 0
            while (true) {
                channel.send(i++)
                delay(100)
            }
        }

        consumer.join()
        producer.join()

    }

    private suspend fun channelTest2() {
        val consumer = lifecycleScope.actor<Int> {

        }

        val producer = lifecycleScope.produce<Int> {

        }
    }
}