package heartzert.test.all.samples.coordinatelayout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar
import heartzert.test.all.R
import kotlin.math.abs

class CoordinateLayoutAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinate_layout)

        val image = findViewById<ImageView>(R.id.image)
        val appbar = findViewById<AppBarLayout>(R.id.appbar)
        val layout = findViewById<ViewGroup?>(R.id.layout)

        image.setBackgroundColor(ContextCompat.getColor(this@CoordinateLayoutAct, R.color.red))

        appbar.addOnOffsetChangedListener(
            object : OnOffsetChangedListener {
                val imageHeight = image.layoutParams.height
                val barHeight = appbar.height
                var lastInt = 0
                val image_height = 200
                val appLayoutHeight = 400
                val minAppLayoutHeight = 200
                val min_image_height = 100
                override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                    Log.d("========", "OnOffsetChangedListener:p1=$p1")
                    Log.d("========", "$imageHeight")
                    Log.d("========", "$barHeight")
                    if (p1 == lastInt) return
                    lastInt = p1
                    val percent = 1 - abs(p1.toFloat()) / (appLayoutHeight - minAppLayoutHeight)
                    if (percent > 0) {
                        val layout = image.layoutParams
                        layout.height = (min_image_height + (imageHeight - min_image_height) * percent).toInt()
                        layout.width = (min_image_height + (imageHeight - min_image_height) * percent).toInt()
                        image.layoutParams = layout
                    }
                    Log.d("========", "persent=$percent")
                    layout?.translationY = abs(p1.toFloat()) - min_image_height / 2 * (1 - percent)

                }
            }

        )
    }

    fun popSnackBar(view: View) {
        Snackbar.make(view, "Test", Snackbar.LENGTH_LONG)
            .setAction("ttt") { Toast.makeText(this, "snack", Toast.LENGTH_LONG).show() }
            .show()
    }
}
