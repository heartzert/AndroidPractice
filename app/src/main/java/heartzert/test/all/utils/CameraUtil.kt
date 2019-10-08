package heartzert.test.all.utils

import android.content.res.Resources
import android.graphics.Camera

/**
 * Created by heartzert on 2019/9/30.
 * Email: heartzert@163.com
 */

fun Camera.setLocationZ(float: Float) {
    setLocation(0f, 0f, float)
}

fun Camera.fitLoacationZ() {
    setLocationZ(-8 * Resources.getSystem().displayMetrics.density)
}