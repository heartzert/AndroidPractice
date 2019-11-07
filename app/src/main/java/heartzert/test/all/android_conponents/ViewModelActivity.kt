package heartzert.test.all.android_conponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import heartzert.test.all.R
import heartzert.test.all.databinding.ActivityViewModelViewBinding

class ViewModelActivity : AppCompatActivity() {

    var viewModel: ViewModelActivityVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelActivityVMFactory(savedInstanceState)).get(ViewModelActivityVM::class.java)
        val binding =
            DataBindingUtil.setContentView<ActivityViewModelViewBinding>(this, R.layout.activity_view_model)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

}
