package heartzert.test.all.uitest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R

class UITestAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uitest)
    }

    fun startNewAct(view: View) {
        startActivity(Intent(this, MiniProgramActivity::class.java))
    }
}
