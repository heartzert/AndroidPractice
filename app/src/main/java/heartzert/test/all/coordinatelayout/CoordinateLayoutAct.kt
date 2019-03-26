package heartzert.test.all.coordinatelayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
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
