package heartzert.test.all.uitest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import heartzert.test.all.R
import heartzert.test.all.databinding.TextSizeTestView

class TextSizeTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<TextSizeTestView>(this, R.layout.activity_text_size_test)
    }

}
