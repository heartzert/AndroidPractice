package heartzert.test.all.uitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityButtonBinding

class ButtonActivity : AppCompatActivity() {

    val text = ObservableField<String>("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityButtonBinding>(this, R.layout.activity_button)
        binding.viewModel = this
        binding.scrollView.setListener {
            Log.d("==========", "scrollStop: ")
        }
    }

    fun click(view: View) {
        text.set(text.get() + "123")
    }
}