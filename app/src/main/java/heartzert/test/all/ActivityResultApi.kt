package heartzert.test.all

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.atomic.AtomicInteger

/**
 * 由于[ComponentActivity.registerForActivityResult]内部有校验生命周期导致不能在任何位置注册，
 * 所以通过[ActivityResultRegistry]主动注册，然后绑定生命周期自己做释放
 * https://www.jianshu.com/p/ff5b6161c29c
 */

private val nextLocalRequestCode = AtomicInteger()

fun <I, O> ComponentActivity.startContractForResult(
    contract: ActivityResultContract<I, O>,
    input: I,
    callback: ActivityResultCallback<O>
) {
    val key = "activity_rq_for_result#${nextLocalRequestCode.getAndIncrement()}"
    val registry = activityResultRegistry
    var launcher: ActivityResultLauncher<I>? = null
    val observer = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (Lifecycle.Event.ON_DESTROY == event) {
                launcher?.unregister()
                lifecycle.removeObserver(this)
            }
        }
    }
    lifecycle.addObserver(observer)
    val newCallback = ActivityResultCallback<O> {
        launcher?.unregister()
        lifecycle.removeObserver(observer)
        callback.onActivityResult(it)
    }
    launcher = registry.register(key, contract, newCallback)
    launcher.launch(input)
}