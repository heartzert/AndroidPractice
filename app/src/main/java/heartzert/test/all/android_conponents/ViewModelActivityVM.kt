package heartzert.test.all.android_conponents

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by heartzert on 2019/11/6.
 * Email: heartzert@163.com
 */
class ViewModelActivityVM(savedInstance: Bundle?) : ViewModel() {

    companion object {
        private const val SAVE_INFO_NUMBER = "InfoNumber"
    }

    val text = MutableLiveData<String>("你好")

    val num = MutableLiveData<Int>(0)

    init {
        if (savedInstance != null) {
            num.value = savedInstance.getInt(SAVE_INFO_NUMBER)
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVE_INFO_NUMBER, num.value!!)
    }

    fun onButtonClick() {
        num.value = num.value?.plus(1)
    }
}