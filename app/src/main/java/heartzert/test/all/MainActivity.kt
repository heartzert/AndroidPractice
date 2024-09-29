package heartzert.test.all

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import heartzert.test.all.databinding.ActivityMainBinding
import heartzert.test.all.samples.coroutines.CoroutinesActivity
import heartzert.test.all.samples.customCamera.CustomCameraActivity
import heartzert.test.all.uitest.CommonUITestActivity
import heartzert.test.all.uitest.canvas.CanvasActivity

/**
 * 打开app的首页，各个测试、例子模块的入口应放在这里
 */
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val testActivityBtns = listOf<Class<out Activity>>(
        CanvasActivity::class.java,
        CoroutinesActivity::class.java,
        CustomCameraActivity::class.java,
        CommonUITestActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(mViewModel)
        mViewModel.initBtns(this, binding.recyclerView, testActivityBtns)
    }
}
