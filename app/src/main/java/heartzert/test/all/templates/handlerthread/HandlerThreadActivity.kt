package heartzert.test.all.templates.handlerthread

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import heartzert.test.all.R

class HandlerThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread)

        val ht = HandlerThread("testHandlerThread")
        ht.start()
        val handler = MyHandler(ht.looper)

        val m = Message.obtain()
        m.what = 23
        handler.sendMessage(m)

        handler.post {
            Log.d("========", "post")
        }
    }

    class MyHandler: Handler {

        constructor() : super()
        constructor(looper: Looper): super(looper)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Log.d("========", "msg.what=${msg?.what}")
        }
    }
}
