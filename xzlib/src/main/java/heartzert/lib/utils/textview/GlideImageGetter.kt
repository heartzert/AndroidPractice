package heartzert.lib.utils.textview

/**
 * Created by Jesson_Yi on 2016/6/22.
 */
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Drawable.Callback
import android.net.Uri
import android.text.Html.ImageGetter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.load.resource.gif.GifDrawable.LOOP_FOREVER
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import heartzert.lib.R
import heartzert.lib.utils.getScreenWidth
import java.util.HashSet
import java.util.Locale

/**
 * 在Textview中使用Html
 */
class GlideImageGetter(private val mTextView: TextView) : ImageGetter {
    private val targets: HashSet<Target<*>> = HashSet()
    private val gifDrawables: HashSet<GifDrawable> = HashSet()

    fun recycle() {
        targets.clear()
        for (gifDrawable in gifDrawables) {
            gifDrawable.callback = null
            gifDrawable.recycle()
        }
        gifDrawables.clear()
    }

    override fun getDrawable(url: String): Drawable {
        val urlDrawable = UrlDrawable()
        if (Uri.parse(url).path?.toLowerCase(Locale.getDefault())?.endsWith(".gif") == true) {
            val target = GifTarget(urlDrawable)
            targets.add(target)
            Glide.with(mTextView.context).asGif().load(url).into(target)
        } else {
            val target = BitmapTarget(urlDrawable)
            targets.add(target)
            Glide.with(mTextView.context).asBitmap().load(url).into(target)
        }
        return urlDrawable
    }

    inner class GifTarget constructor(private val urlDrawable: UrlDrawable) : SimpleTarget<GifDrawable?>() {

        override fun onResourceReady(resource: GifDrawable, transition: Transition<in GifDrawable?>?) {
            val w: Int = getScreenWidth()
            var hh = resource.intrinsicHeight
            var ww = resource.intrinsicWidth

            if (hh == 0) hh = 1
            if (ww == 0) ww = 1

            val high = hh * w / ww
            val rect = Rect(0, 0, w, high)
            resource.bounds = rect
            urlDrawable.bounds = rect
            urlDrawable.drawable = resource
            gifDrawables.add(resource)
            resource.callback = mTextView
            resource.start()
            resource.setLoopCount(LOOP_FOREVER)
            mTextView.text = mTextView.text
            mTextView.invalidate()
        }
    }

    private inner class BitmapTarget(private val urlDrawable: UrlDrawable) : SimpleTarget<Bitmap?>() {

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
            val drawable: Drawable = BitmapDrawable(mTextView.context.resources, resource)
            val w: Int = getScreenWidth()
            var hh = drawable.intrinsicHeight
            var ww = drawable.intrinsicWidth

            if (hh == 0) hh = 1
            if (ww == 0) ww = 1

            val high = hh * w / ww
            val rect = Rect(0, 0, w, high)
            drawable.bounds = rect
            urlDrawable.bounds = rect
            urlDrawable.drawable = drawable
            mTextView.text = mTextView.text
            mTextView.invalidate()
        }
    }

    init {
        mTextView.setTag(R.id.comment, this)
    }

    /**
     * Created by Jesson_Yi on 2016/6/23.
     */
    class UrlDrawable : BitmapDrawable(), Callback {

        var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            scheduleSelf(what, `when`)
        }

        override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            unscheduleSelf(what)
        }

        override fun invalidateDrawable(who: Drawable) {
            invalidateSelf()
        }

    }
}