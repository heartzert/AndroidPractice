package heartzert.test.all.contentprovider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import heartzert.test.all.R

class TestProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_provider)

        val uri = Uri.parse("content://heartzert.test.all.contentprovider.TestProvider")
        contentResolver.query(uri, null, null, null, null)
    }
}
