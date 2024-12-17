package heartzert.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * ViewBinding基类
 */
abstract class ViewBindingActivity<T : ViewBinding>(val inflate: (LayoutInflater) -> T) : AppCompatActivity() {

    protected val binding: T by lazy { inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}