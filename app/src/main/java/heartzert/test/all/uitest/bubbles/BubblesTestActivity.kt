package heartzert.test.all.uitest.bubbles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import heartzert.test.all.R

class BubblesTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubbles_test)
    }

    fun button1(view: View) {
        if (view !is Button) {
            return
        }
        Toast.makeText(this, view.text, Toast.LENGTH_LONG).show()
    }
}