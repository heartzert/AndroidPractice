package heartzert.test.all.samples.softkeyboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import heartzert.lib.utils.tools.SoftKeyboardListener
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivitySoftKeyboardBinding

class SoftKeyboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoftKeyboardBinding

    private val softKeyboardListener by lazy {
        SoftKeyboardListener(this, object : SoftKeyboardListener.Callback {
            override fun onKeyboardUp(softKeyboardHeight: Int) {
                Log.d("==========wxz", "softKeyboardHeight up: $softKeyboardHeight")
                binding.textView.translationY = -softKeyboardHeight.toFloat()
            }

            override fun onKeyboardDown() {
                Log.d("==========wxz", "softKeyboardHeight down")
                binding.textView.translationY = 0f
            }

        })
    }

    init {
        lifecycle.addObserver(softKeyboardListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_soft_keyboard)
    }

}