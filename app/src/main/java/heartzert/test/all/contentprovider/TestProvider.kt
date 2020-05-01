package heartzert.test.all.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * Created by heartzert on 2020/5/1.
 * Email: heartzert@163.com
 */
class TestProvider: ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("====", "insert in thread : ${getThread()}")
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d("====", "query in thread : ${getThread()}")
        return null
    }

    override fun onCreate(): Boolean {
        Log.d("====", "onCreate in thread : ${getThread()}")
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        Log.d("====", "update in thread : ${getThread()}")
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d("====", "delete in thread : ${getThread()}")
        return 0
    }

    override fun getType(uri: Uri): String? {
        Log.d("====", "getType in thread : ${getThread()}")
        return null
    }

    private fun getThread(): String {
        return Thread.currentThread().name
    }
}