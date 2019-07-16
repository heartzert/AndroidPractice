package heartzert.test.all.coordinatelayout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import heartzert.test.all.R

class CoordinateLayoutAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinate_layout)
    }

    fun popSnackBar(view: View) {
        Snackbar.make(view, "Test", Snackbar.LENGTH_LONG)
            .setAction("ttt") { Toast.makeText(this, "snack", Toast.LENGTH_LONG).show() }
            .show()
    }
}
