package heartzert.test.all.tts

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import heartzert.lib.base.ViewBindingActivity
import heartzert.lib.utils.safeResume
import heartzert.test.all.databinding.ActivityTtsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Created by heartzert on 2025/02/28.
 * Email: heartzert@163.com
 */
class TTSActivity : ViewBindingActivity<ActivityTtsBinding>(ActivityTtsBinding::inflate) {

    var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TTSManager.initTextToSpeech()

        lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                delay(100)
                if ((TTSManager.supportEngines.size > 0)) {
                    break
                }
            }
            withContext(Dispatchers.Main) {
                initMe()
            }
        }

    }

    private fun initMe() {
        tts = TextToSpeech(this, {
            if (it == TextToSpeech.SUCCESS) {
                if (binding.tvVoice.text.isNotEmpty()) {
                    TTSManager.supportEngines.find { it.engineInfo.name == TTSManager.defaultEngine }?.supportVoice?.find { it.name == binding.tvVoice.text }?.let {
                        tts?.voice = it
                    }
                }
            }
        }, "com.google.android.tts")
        binding.tvEngine.text = TTSManager.defaultEngine

        binding.btnEngine.setOnClickListener {
            //弹出一个dialog，选择引擎
            getEngineDialog().show()
        }

        binding.btnVoice.setOnClickListener {
            //弹出一个dialog，选择声音
            getVoiceDialog().show()
        }

        binding.btnRead.setOnClickListener {
            tts?.speak(binding.editText.text.toString(), TextToSpeech.QUEUE_FLUSH, null, System.currentTimeMillis().toString())
        }
    }

    private fun setEngine(engine: String) {
        lifecycleScope.launch {
            tts = getNewTTS(this@TTSActivity, engine)
            tts?.setLanguage(Locale.forLanguageTag("zh-CN"))
            if (binding.tvVoice.text.isNotEmpty()) {
                TTSManager.supportEngines.find { it.engineInfo.name == engine }?.supportVoice?.find { it.name == binding.tvVoice.text }?.let {
                    tts?.voice = it
                }
            }
            binding.tvEngine.text = engine
        }
    }

    //一个dialog，用于选择引擎或声音
    fun getEngineDialog(): Dialog {
        return AlertDialog.Builder(this)
            .setTitle("选择引擎")
            .setItems(TTSManager.supportEngines.map { it.engineInfo.name }.toTypedArray()) { dialog, which ->
                setEngine(TTSManager.supportEngines[which].engineInfo.name)
            }
            .create()
    }

    fun getVoiceDialog(): Dialog {
        val voices = TTSManager.supportEngines.find { it.engineInfo.name == binding.tvEngine.text }?.supportVoice?.sortedBy { it.name }
        return AlertDialog.Builder(this)
            .setTitle("选择声音")
            .setItems(voices?.map { it.name }?.toTypedArray() ?: arrayOf()) { dialog, which ->
                tts?.voice = voices?.get(which)
                binding.tvVoice.text = voices?.get(which)?.name
            }
            .create()
    }


    private suspend fun getNewTTS(context: Context, engine: String? = null): TextToSpeech? {
        return suspendCancellableCoroutine {
            var tts: TextToSpeech? = null
            tts = TextToSpeech(context, { status ->
                if (status == TextToSpeech.SUCCESS) {
                    it.safeResume(tts)
                } else {
                    it.safeResume(null)
                }
            }, engine)
        }
    }
}