package heartzert.test.all.contentprovider

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import heartzert.test.all.R
import heartzert.test.all.binder.Book

class TestProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_provider)

        val bookUri = Uri.parse("content://heartzert.test.all.contentprovider.TestProvider/book")
        val userUri = Uri.parse("content://heartzert.test.all.contentprovider.TestProvider/user")

        val bookValue = ContentValues().apply {
            put("_id", 6)
            put("name", "HeartzertBook")
        }
        val userValue = ContentValues().apply {
            put("_id", 6)
            put("name", "Heartzert")
            put("sex", 1)
        }
        contentResolver.insert(bookUri, bookValue)
        val bookcursor = contentResolver.query(bookUri, null, null, null, null)
        while (bookcursor.moveToNext()) {
            val book = Book()
            book.name = bookcursor.getString(1)
            Log.d("=======", book.name)
        }
        bookcursor.close()
    }
}
