package heartzert.test.all.uitest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R
import java.text.DateFormat
import java.util.*
import java.util.regex.Pattern

class UITestAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uitest)

        val hk = Locale.TRADITIONAL_CHINESE
        val us = Locale.US
        val uk = Locale.UK

        Log.d("==========wxz", "hk: ${DateFormat.getDateInstance(DateFormat.SHORT, hk).format(Date())}")
        Log.d("==========wxz", "us: ${DateFormat.getDateInstance(DateFormat.SHORT, us).format(Date())}")
        Log.d("==========wxz", "uk: ${DateFormat.getDateInstance(DateFormat.SHORT, uk).format(Date())}")

//        DraftRenameUtils.checkAndRename(projectList)
//
//        DraftRenameUtils.getCopyName(projectList.first())

        projectList.mapNotNull { it?.create_time }
        Log.d("==========wxz", "$projectList")

        Log.d("==========wxz", ": ");
    }

    fun startNewAct(view: View) {
        startActivity(Intent(this, MiniProgramActivity::class.java))
    }

    fun String.keepDigital(): String {
        val newString = StringBuffer()
        val matcher = Pattern.compile("\\d").matcher(this)
        while (matcher.find()) {
            newString.append(matcher.group())
        }
        return newString.toString()
    }
}
