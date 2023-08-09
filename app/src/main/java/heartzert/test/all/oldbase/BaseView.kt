package heartzert.test.all.oldbase

import android.app.Activity
import android.content.Intent

/**
 * Created by heartzert on 2018/6/13.
 * Email: heartzert@163.com
 */

interface BaseView {
    fun finishActivity()
    fun initToolbar()
    fun setTransparent() {}
    fun getActivityIntent(): Intent {
        return Intent()
    }
    fun requestPermissions(permissions: Array<String>, requestCode: Int)

    fun isActivityFinishing(): Boolean
    fun getActivity(): Activity
    fun setActivityIntent(intent: Intent?) {}
    fun setActivityResult(resultCode: Int, intent: Intent?, ifFinishActivity: Boolean) {}
    fun setActivityEmptyResult(resultCode: Int, ifFinishActivity: Boolean) {}
}