package heartzert.test.all.asynctask

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R
import kotlinx.android.synthetic.main.activity_async_task_test.text

class AsyncTaskTestAct : AppCompatActivity() {
    private var task: AsyncTask<String, Double, Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task_test)
    }

    fun button(view: View) {
        @SuppressLint("StaticFieldLeak")
        task = object : AsyncTask<String, Double, Boolean>() {
            override fun doInBackground(vararg params: String?): Boolean {
                for (i in 1..100) {
                    publishProgress(i.toDouble())
                    Thread.sleep(100)
                }
                return true
            }

            override fun onProgressUpdate(vararg values: Double?) {
                super.onProgressUpdate(*values)
                text.text = "${values[0]}"
            }

            override fun onPostExecute(result: Boolean?) {
                super.onPostExecute(result)
                text.text = "done"
                task = null
            }

            override fun onCancelled(result: Boolean?) {
                text.text = "canceled"
                task = null
            }

            override fun onPreExecute() {
                Toast.makeText(this@AsyncTaskTestAct, "开始了", Toast.LENGTH_SHORT).apply {
                    this.setMargin(10f, 10f)
                }.show()
                super.onPreExecute()
            }
        }
        task?.execute("")
    }

    fun cancel(view: View) {
        //设置为true会立即中断thread并显示调用onCancel，设为false会等待thread执行结束，最后不调用onPostExecute，调用onCancel
        task?.cancel(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        task?.cancel(true)
    }
}
