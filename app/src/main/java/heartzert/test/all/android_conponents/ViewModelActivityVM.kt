package heartzert.test.all.android_conponents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by heartzert on 2019/11/6.
 * Email: heartzert@163.com
 */
class ViewModelActivityVM: ViewModel() {

    private val _text = MutableLiveData<String>("你好")

    val text: LiveData<String>
        get() = _text

    private val _num = MutableLiveData<Int>(0)

    val num : LiveData<Int>
        get() = _num

    fun onButtonClick() {
        _num.value =  _num.value?.plus(1)
    }
}