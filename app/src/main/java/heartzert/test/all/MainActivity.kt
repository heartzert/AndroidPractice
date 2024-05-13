package heartzert.test.all

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import heartzert.test.all.databinding.ActivityMainBinding
import heartzert.test.all.samples.coroutines.CoroutinesActivity
import heartzert.test.all.samples.softkeyboard.SoftKeyboardActivity
import heartzert.test.all.uitest.UITestAct
import heartzert.test.all.uitest.canvas.CanvasActivity

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val testActivityBtns = listOf<Class<out Activity>>(
        CanvasActivity::class.java,
        CoroutinesActivity::class.java,
        SoftKeyboardActivity::class.java,
        UITestAct::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(mViewModel)
        mViewModel.initBtns(this, binding.recyclerView, testActivityBtns)
    }
}
