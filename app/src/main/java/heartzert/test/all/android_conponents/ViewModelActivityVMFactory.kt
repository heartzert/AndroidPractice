package heartzert.test.all.android_conponents

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by heartzert on 2019/11/7.
 * Email: heartzert@163.com
 */
class ViewModelActivityVMFactory(private val savedInstance: Bundle?): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelActivityVM(savedInstance) as T
    }
}