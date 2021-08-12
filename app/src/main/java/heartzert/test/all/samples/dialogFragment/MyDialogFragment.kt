package heartzert.test.all.samples.dialogFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import heartzert.test.all.R

/**
 * Created by heartzert on 6/23/21.
 * Email: heartzert@163.com
 */
class MyDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //背景透明
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.dialog_full_screen_test, container)
    }

    override fun onStart() {
        super.onStart()
        //需要放在onStart。在onCreateView是不行的，其它时机待测试
        dialog?.window?.let {
            it.decorView.setPadding(0, 0, 0, 0)
            val lp: WindowManager.LayoutParams = it.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.gravity = Gravity.BOTTOM
            it.attributes = lp
        }
    }

}