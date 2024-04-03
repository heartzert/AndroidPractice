package heartzert.test.all.uitest.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import heartzert.test.all.R
import heartzert.test.all.custom_views.CircularProgressBar

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        findViewById<CircularProgressBar>(R.id.progress_bar).setProgress(100)
    }
}