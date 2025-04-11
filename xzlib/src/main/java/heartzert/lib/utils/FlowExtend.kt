package heartzert.lib.utils

import androidx.lifecycle.*
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.resume

/**
 * Created by heartzert on 2023/4/27.
 * Email: heartzert@163.com
 */

/**
 * 针对冷流的扩展方法。
 * launchWhenXXX会在离开对应的生命周期时挂起协程，而不是销毁协程。所以此时，上游的Flow仍然在生产数据。
 * flowWithLifecycle内部是使用repeatOnLifecycle实现的，会在离开对应的生命周期时销毁协程。
 * 由于冷流需要有一个订阅者才会开始生产数据，所以此时会停止生产，从而节省资源。
 */
fun <T> Flow<T>.collectRepeatOnCreated(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectRepeatOnCreated.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.CREATED).collect {
            block(it)
        }
    }
}

fun <T> Flow<T>.collectRepeatOnStarted(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectRepeatOnStarted.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED).collect {
            block(it)
        }
    }
}

fun <T> Flow<T>.collectRepeatOnResumed(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        this@collectRepeatOnResumed.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.RESUMED).collect {
            block(it)
        }
    }
}

fun <T> StateFlow<T>.collectInViewmodel(viewModel: ViewModel, block: suspend (T) -> Unit) {
    viewModel.viewModelScope.launch {
        collect {
            block(it)
        }
    }
}

fun <T> StateFlow<T>.collectWhenCreated(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenCreate {
        collect {
            block(it)
        }
    }
}

fun <T> StateFlow<T>.collectWhenStarted(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenStart {
        collect {
            block(it)
        }
    }
}

fun <T> StateFlow<T>.collectWhenResumed(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenResume {
        collect {
            block(it)
        }
    }
}

fun <T> SharedFlow<T>.collectInViewmodel(viewModel: ViewModel, block: suspend (T) -> Unit) {
    viewModel.viewModelScope.launch {
        collect {
            block(it)
        }
    }
}

fun <T> SharedFlow<T>.collectWhenCreated(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenCreate {
        collect {
            block(it)
        }
    }
}

fun <T> SharedFlow<T>.collectWhenStarted(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenStart {
        collect {
            block(it)
        }
    }
}

fun <T> SharedFlow<T>.collectWhenResumed(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenResume {
        collect {
            block(it)
        }
    }
}

fun <T> MutableStateFlow<T>.emitThisInViewModel(viewModel: ViewModel, block: T.() -> Unit) {
    viewModel.viewModelScope.launch {
        this@emitThisInViewModel.value.block()
        emit(this@emitThisInViewModel.value)
    }
}

fun <T> MutableSharedFlow<T>.emitInViewModel(viewModel: ViewModel, value: T) {
    viewModel.viewModelScope.launch {
        emit(value)
    }
}

fun <T> SimpleMutableSharedFlow() = MutableSharedFlow<T>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


/**
 * 规避警告
 */
fun LifecycleCoroutineScope.launchWhenCreate(block: suspend CoroutineScope.() -> Unit): Job = launchWhenCreated(block)

/**
 * 规避警告
 */
fun LifecycleCoroutineScope.launchWhenStart(block: suspend CoroutineScope.() -> Unit): Job = launchWhenStarted(block)

/**
 * 规避警告
 */
fun LifecycleCoroutineScope.launchWhenResume(block: suspend CoroutineScope.() -> Unit): Job = launchWhenResumed(block)

fun <T> CancellableContinuation<T>.safeResume(value: T) {
    if (isActive) {
        resume(value)
    }
}