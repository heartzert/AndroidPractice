package heartzert.lib.utils

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable

/**
 * Created by heartzert on 2020/1/8.
 * Email: heartzert@163.com
 */
object ImageUtil {

    /**
     * 缩放Drawable
     */
    fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Drawable {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp= drawableToBitmap(drawable)
        val matrix = Matrix()
        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)
        val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true)
        return BitmapDrawable(null, newbmp)
    }

    /**
     * 缩放BitmapDrawable
     */
    fun zoomBitmapDrawable(drawable: BitmapDrawable, w:Int, h: Int): BitmapDrawable {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp = drawable.bitmap
        val matrix = Matrix()
        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)
        val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true)
        val newDrawable = BitmapDrawable(null, newbmp)
        val rect = newDrawable.bounds
        rect.set(rect.left, rect.top, w, h)
        return newDrawable
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE)
            Bitmap.Config.ARGB_8888
        else
            Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 获取src图片的Bitmap
     */
    fun getImage(resources: Resources, srcId: Int, width: Int, height : Int): Bitmap {
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