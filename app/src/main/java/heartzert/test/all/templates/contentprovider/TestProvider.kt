package heartzert.test.all.templates.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 * Created by heartzert on 2020/5/1.
 * Email: heartzert@163.com
 */
class TestProvider: ContentProvider() {

    companion object {
        const val AUTHORITY = "heartzert.test.all.templates.contentprovider.TestProvider"
        val BOOK_DB_URL = Uri.parse("content://$AUTHORITY/book")
        val USER_DB_URL = Uri.parse("content://$AUTHORITY/user")

        const val BOOK_URI_CODE = 0
        const val USER_URI_CODE = 1
    }

    var db : SQLiteDatabase? = null
    val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, "book", BOOK_URI_CODE)
        addURI(AUTHORITY, "user", USER_URI_CODE)
    }

    override fun onCreate(): Boolean {
        Log.d("====", "onCreate in thread : ${getThread()}")
        db = DbHelper(context).writableDatabase
        db!!.execSQL("delete from ${DbHelper.BOOK_TABLE_NAME}")
        db!!.execSQL("delete from ${DbHelper.USER_TABLE_NAME}")
        db!!.execSQL("insert into ${DbHelper.BOOK_TABLE_NAME} values(3,'Android');")
        db!!.execSQL("insert into ${DbHelper.BOOK_TABLE_NAME} values(4,'iOS');")
        db!!.execSQL("insert into ${DbHelper.BOOK_TABLE_NAME} values(5,'Flutter');")
        db!!.execSQL("insert into ${DbHelper.USER_TABLE_NAME} values(1,'WXZ',0);")
        db!!.execSQL("insert into ${DbHelper.USER_TABLE_NAME} values(2,'Flutter',1);")
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("====", "insert in thread : ${getThread()}")
        val table = getTableName(uri)
        db?.insert(table, null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d("====", "query in thread : ${getThread()}")
        val tableName = getTableName(uri)
        return db?.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        Log.d("====", "update in thread : ${getThread()}")
        val tableName = getTableName(uri)
        val result = db!!.update(tableName, values, selection, selectionArgs)
        if (result > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return result
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d("====", "delete in thread : ${getThread()}")
        val table = getTableName(uri)
        val result = db!!.delete(table, selection, selectionArgs)
        if (result > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return result
    }

    override fun getType(uri: Uri): String? {
        Log.d("====", "getType in thread : ${getThread()}")
        return null
    }

    private fun getThread(): String {
        return Thread.currentThread().name
    }

    private fun getTableName(uri: Uri): String {
        return when(uriMatcher.match(uri)) {
            BOOK_URI_CODE -> {
                DbHelper.BOOK_TABLE_NAME
            }
            USER_URI_CODE -> {
                DbHelper.USER_TABLE_NAME
            }
            else -> {
                ""
            }
        }
    }

    class DbHelper : SQLiteOpenHelper {

        companion object {
            const val BOOK_TABLE_NAME = "book"
            const val USER_TABLE_NAME = "user"
            const val DB_NAME = "TestDb"
            const val DB_VERSION = 1
        }

        constructor(context: Context?): super(context, DB_NAME, null, DB_VERSION)

        constructor(context: Context?,
            name: String?,
            factory: SQLiteDatabase.CursorFactory?,
            version: Int
        ) : super(context, name, factory, version)

        constructor(
            context: Context?,
            name: String?,
            factory: SQLiteDatabase.CursorFactory?,
            version: Int,
            errorHandler: DatabaseErrorHandler?
        ) : super(context, name, factory, version, errorHandler)

        @RequiresApi(Build.VERSION_CODES.P)
        constructor(
            context: Context?,
            name: String?,
            version: Int,
            openParams: SQLiteDatabase.OpenParams
        ) : super(context, name, version, openParams)

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table if not exists $USER_TABLE_NAME (_id integer primary key,name TEXT,sex INT)")
            db?.execSQL("create table if not exists $BOOK_TABLE_NAME (_id integer primary key,name TEXT)")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

    }
}