package heartzert.test.all.samples.mini_program

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import heartzert.test.all.R

/**
 * 放在另一个taskAffinity任务栈，来模仿小程序可以在最近任务栏有多个实例的情况
 */
class MiniProgramActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mini_program)
    }
}
