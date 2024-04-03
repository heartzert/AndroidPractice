package heartzert.test.all.samples.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by heartzert on 2024/3/25.
 * Email: heartzert@163.com
 */
class MyContract : ActivityResultContract<String, String>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, ContractSecondActivity::class.java).apply {
            putExtra(INPUT_KEY, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return intent?.getStringExtra(OUTPUT_KEY) ?: ""
    }

    companion object {
        const val INPUT_KEY = "param"
        const val OUTPUT_KEY = "result"
    }
}

class MyContractRegister(private val activityResultRegistry: ActivityResultRegistry): DefaultLifecycleObserver {

    lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launcher = activityResultRegistry.register("1", owner, MyContract()) {
            println("MyContractRegister: $it")
        }
    }

    fun launchContract() {
        launcher.launch("first page")
    }
}