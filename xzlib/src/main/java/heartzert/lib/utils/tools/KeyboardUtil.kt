package heartzert.lib.utils.tools

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by heartzert on 2020/12/28.
 * Email: heartzert@163.com
 */

fun EditText.showKeyboard() = showKeyBoard(this)
fun EditText.hideKeyboard() = hideKeyBoard(this)

/**
 * 显示软键盘
 */
fun showKeyBoard(editText: EditText) {
    editText.isFocusable = true
    editText.isFocusableInTouchMode = true
    editText.requestFocus()
    editText.findFocus()
    val inputManager =
        editText.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(editText, 0)
}

/**
 * 隐藏软键盘
 */
fun hideKeyBoard(editText: EditText) {
    val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}