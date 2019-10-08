package heartzert.test.all.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by heartzert on 2019/9/30.
 * Email: heartzert@163.com
 */
object ImageUtil {
    fun getImage(resources: Resources?, srcId: Int, width: Int, height : Int): Bitmap? {
        resources ?: return null
        val density = if (width > height) height else width
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, srcId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = density
        return BitmapFactory.decodeResource(resources, srcId, options)
    }
}