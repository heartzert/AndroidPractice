package heartzert.test.all.uitest.canvas

import android.os.Bundle
import heartzert.lib.base.ViewBindingActivity
import heartzert.test.all.databinding.ActivityCanvasBinding

class A_CanvasTestActivity : ViewBindingActivity<ActivityCanvasBinding>(ActivityCanvasBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置清空按钮点击事件
        binding.btnClear.setOnClickListener {
            binding.paintView.clear()
        }
    }

}