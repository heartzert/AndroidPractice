package heartzert.test.all.samples.contract

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by heartzert on 2024/3/25.
 * Email: heartzert@163.com
 */
class ContractSecondActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input = intent.getStringExtra(MyContract.INPUT_KEY)?.also {
            println("ContractSecondActivity: $it")
        } ?: "noting get"

        setResult(RESULT_OK, Intent().apply {
            putExtra(MyContract.OUTPUT_KEY, "$input from second activity")
        })
    }
}