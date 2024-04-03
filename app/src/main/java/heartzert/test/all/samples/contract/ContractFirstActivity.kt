package heartzert.test.all.samples.contract

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by heartzert on 2024/3/25.
 * Email: heartzert@163.com
 */
class ContractFirstActivity: AppCompatActivity() {

    private val contractRegister by lazy { MyContractRegister(this.activityResultRegistry) }

    init {
        lifecycle.addObserver(contractRegister)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contractRegister.launchContract()
    }
}