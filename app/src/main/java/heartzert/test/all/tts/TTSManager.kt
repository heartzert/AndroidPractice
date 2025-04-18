package heartzert.test.all.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.EngineInfo
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import android.widget.Toast
import heartzert.lib.utils.safeResume
import heartzert.test.all.archive.oldbase.applicationContext
import heartzert.test.all.tts.TTSManager.MyTextToSpeech.TTSListener
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Created by heartzert on 2025/01/02.
 * Email: heartzert@163.com
 */
object TTSManager {

    //支持的引擎列表
    val supportEngines = arrayListOf<TTSEngineInfo>()

    //默认引擎
    var defaultEngine = ""

    //谷歌语音引擎包名
    private const val GOOGLE_TTS_ENGINE = "com.google.android.tts"

    //语音阅读超时毫秒数，超时则认为播放失败
    private const val TIME_OUT_MILLIS = 120L

    //最大同时加载引擎数
    private const val MAX_ENGINE_COUNT = 1

    private var sourceTextToSpeechList: ArrayList<MyTextToSpeech> = arrayListOf()
    private var targetTextToSpeechList: ArrayList<MyTextToSpeech> = arrayListOf()

    private var sourceReadListener: ArrayList<TTSReadListener> = arrayListOf()
    private var targetReadListener: ArrayList<TTSReadListener> = arrayListOf()

    //当前正在播放的任务
    private var readJob: Job? = null

    fun initTextToSpeech() {
        MainScope().launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }) {
            //初始化时先获取支持的引擎列表
            withContext(Dispatchers.Default) {
                var tmpTTS = getNewTTS(applicationContext) ?: return@withContext
                val engines = tmpTTS.engines
                defaultEngine = tmpTTS.defaultEngine
                tmpTTS.shutdown()
                for (engine in engines) {
                    tmpTTS = getNewTTS(applicationContext, engine.name) ?: continue
                    supportEngines.add(TTSEngineInfo(engine, tmpTTS.availableLanguages?.toList() ?: listOf(), tmpTTS.voices?.toList() ?: listOf()))
                    tmpTTS.shutdown()
                }
                Log.d("=====wxz=====", "initEngines: engines = $supportEngines, defaultEngine = $defaultEngine")
            }
            //创建对应引擎的TTS对象，优先使用谷歌引擎，每个引擎创建2个对象
            val engineNames = supportEngines.map { it.engineInfo.name }.toMutableList()
            while (engineNames.isNotEmpty()) {
                var curEngine = GOOGLE_TTS_ENGINE
                if (engineNames.contains(GOOGLE_TTS_ENGINE)) {
                    sourceTextToSpeechList.add(MyTextToSpeech(engine = GOOGLE_TTS_ENGINE, tagPrefix = "source"))
                    targetTextToSpeechList.add(MyTextToSpeech(engine = GOOGLE_TTS_ENGINE, tagPrefix = "target"))
                } else {
                    curEngine = engineNames[0]
                    sourceTextToSpeechList.add(MyTextToSpeech(engine = curEngine, tagPrefix = "source"))
                    targetTextToSpeechList.add(MyTextToSpeech(engine = curEngine, tagPrefix = "target"))
                }
                engineNames.remove(curEngine)
                if (sourceTextToSpeechList.size >= MAX_ENGINE_COUNT || targetTextToSpeechList.size >= MAX_ENGINE_COUNT) break
            }
        }
    }

    /**
     * 读文本时，每个引擎依次读，直到有一个引擎读成功
     */
    fun readText(readText: String, isSource: Boolean) {
        stopTTS()
        if (readText.isBlank()) return

        readJob?.cancel()
        readJob = MainScope().launch(Dispatchers.Default) {
            run block@{
                if (isSource) {
                    sourceReadListener.forEach { listener ->
                        listener.onStart()
                    }
                    for (sourceTextToSpeech in sourceTextToSpeechList) {
                        if (!isActive) return@block
                        if (readTextSync(sourceTextToSpeech, readText)) {
                            return@block
                        }
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "播放失败", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    targetReadListener.forEach { listener ->
                        listener.onStart()
                    }
                    for (targetTextToSpeech in targetTextToSpeechList) {
                        if (!isActive) return@block
                        if (readTextSync(targetTextToSpeech, readText)) {
                            return@block
                        }
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "播放失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (isSource) {
                sourceReadListener.forEach { listener ->
                    listener.onEnd()
                }
            } else {
                targetReadListener.forEach { listener ->
                    listener.onEnd()
                }
            }
        }
    }

    fun setTTSSpeed() {
        sourceTextToSpeechList.forEach {
            it.setTTSSpeed()
        }
        targetTextToSpeechList.forEach {
            it.setTTSSpeed()
        }
    }

    fun addListener(sourceListener: TTSReadListener?, targetListener: TTSReadListener?) {
        sourceListener?.also {
            sourceReadListener.add(sourceListener)
        }
        targetListener?.also {
            targetReadListener.add(targetListener)
        }
    }

    fun removeListener(sourceListener: TTSReadListener?, targetListener: TTSReadListener?) {
        sourceListener?.also {
            sourceReadListener.remove(sourceListener)
        }
        targetListener?.also {
            targetReadListener.remove(targetListener)
        }
    }

    fun clearListener() {
        sourceReadListener.clear()
        targetReadListener.clear()
    }

    fun changeSourceTTSLan(lanCode: String) {
        sourceTextToSpeechList.forEach {
            MainScope().launch(Dispatchers.Default) {
                it.changeTTSLan(lanCode)
            }
        }
    }

    fun changeTargetTTSLan(lanCode: String) {
        targetTextToSpeechList.forEach {
            MainScope().launch(Dispatchers.Default) {
                it.changeTTSLan(lanCode)
            }
        }
    }

    fun stopTTS() {
        sourceTextToSpeechList.forEach { listener ->
            listener.stopTTS()
        }
        targetTextToSpeechList.forEach { listener ->
            listener.stopTTS()
        }
        sourceReadListener.forEach { listener ->
            listener.onEnd()
        }
        targetReadListener.forEach { listener ->
            listener.onEnd()
        }
        readJob?.cancel()
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

    private suspend fun readTextSync(tts: MyTextToSpeech, readText: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            var tmpListener: TTSListener? = null

            fun clearListenerAndResume(status: Boolean) {
                tts.removeListener(tmpListener)
                continuation.safeResume(status)
            }

            tmpListener = object : TTSListener {
                override fun onStart(utteranceId: String?) {
                }

                override fun onDone(utteranceId: String?) {
                    clearListenerAndResume(true)
                }

                override fun onStop(utteranceId: String?, interrupted: Boolean) {
                    clearListenerAndResume(true)
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    clearListenerAndResume(false)
                }

                override fun onReadFailed(isTimeShort: Boolean) {
                    clearListenerAndResume(false)
                }
            }
            tts.addListener(tmpListener)
            val status = tts.readText(readText)
            if (!status) {
                clearListenerAndResume(false)
            }
        }

    }

    data class TTSEngineInfo(val engineInfo: EngineInfo, val supportLan: List<Locale>, val supportVoice: List<Voice>) {

        override fun toString(): String {
            return "TTSEngineInfo(engineInfo=$engineInfo, supportLan=$supportLan, supportVoice=${supportVoice.map { it.name }})"
        }
    }

    interface TTSReadListener {
        fun onStart()
        fun onEnd()
    }

    internal class MyTextToSpeech(val engine: String? = null, val tagPrefix: String = "") {

        private var mTextToSpeech: TextToSpeech? = null

        private var lanSetResult = true

        private var lastTTSStartTime = 0L

        private var readListener = arrayListOf<TTSListener>()

        init {
            MainScope().launch(Dispatchers.Default) {
                mTextToSpeech = getNewTTS(applicationContext, engine)
                setTTSSpeed()
                setOnUtteranceProgressListener()
            }
        }

        fun addListener(readListener: TTSListener?) {
            readListener?.let { this.readListener.add(readListener) }
        }

        fun removeListener(readListener: TTSListener?) {
            readListener?.let { this.readListener.remove(readListener) }
        }

        fun clearListener() {
            readListener.clear()
        }

        fun changeTTSLan(lanCode: String) {
            MainScope().launch(CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }) {
                val result = mTextToSpeech?.setLanguage(Locale.forLanguageTag(lanCode))
                Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech setLanguage $lanCode result = $result, engine: $engine")
                lanSetResult = result == TextToSpeech.LANG_AVAILABLE
            }
        }

        fun readText(readText: String): Boolean {
            if (!lanSetResult) {
                return false
            }
//            sourceTextToSpeech?.setEngineByPackageName()
//            sourceTextToSpeech?.setOnUtteranceCompletedListener {  }
//            sourceTextToSpeech?.areDefaultsEnforced()
//            sourceTextToSpeech?.synthesizeToFile()
//            sourceTextToSpeech?.playSilence()
//            sourceTextToSpeech?.playEarcon()
//            sourceTextToSpeech?.defaultEngine
//            sourceTextToSpeech?.voices
//            sourceTextToSpeech?.voice
//            sourceTextToSpeech?.availableLanguages
//            sourceTextToSpeech?.defaultVoice
//            sourceTextToSpeech?.engines
//            sourceTextToSpeech?.setAudioAttributes()
//            sourceTextToSpeech?.setEngineByPackageName()
//            sourceTextToSpeech?.defaultEngine
            val status = mTextToSpeech?.speak(readText, TextToSpeech.QUEUE_FLUSH, null, tagPrefix + readText.hashCode().toString())
            Log.d("=====wxz=====", "TTSManager ${tagPrefix}textToSpeech speak status is $status, engine: $engine")
            if (status == TextToSpeech.ERROR) {
                Log.d("=====wxz=====", "TTSManager ${tagPrefix}textToSpeech speak error, lan: ${mTextToSpeech?.voice?.locale}, engine is $engine")
                return false
            }
            return true
        }

        fun stopTTS() {
            mTextToSpeech?.stop()
        }

        fun setTTSSpeed() {
            mTextToSpeech?.setPitch(1.0f) //设置声调
//            when (userDataRepo.getVoiceSpeed()) {
//                0 -> mTextToSpeech?.setSpeechRate(1.0f) //设置语速
//                1 -> mTextToSpeech?.setSpeechRate(0.75f) //设置语速
//                2 -> mTextToSpeech?.setSpeechRate(0.5f) //设置语速
//            }
        }

        private fun setOnUtteranceProgressListener() {
            mTextToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech onStart, engine: $engine")
                    lastTTSStartTime = System.currentTimeMillis()
                    readListener.forEach {
                        it.onStart(utteranceId)
                    }
                }

                override fun onDone(utteranceId: String?) {
                    Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech onDone")
                    val time = System.currentTimeMillis() - lastTTSStartTime
                    Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech read time: $time, engine: $engine")
                    if (time < TIME_OUT_MILLIS) {
                        readListener.forEach {
                            it.onReadFailed(true)
                        }
                    }
                    readListener.forEach {
                        it.onDone(utteranceId)
                    }
                }

                override fun onStop(utteranceId: String?, interrupted: Boolean) {
                    Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech onStop, engine: $engine")
                    readListener.forEach {
                        it.onStop(utteranceId, interrupted)
                    }
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    Log.d("=====wxz=====", "TTSManager ${tagPrefix}TextToSpeech onError, errorCode = $errorCode, engine: $engine")
                    readListener.forEach {
                        it.onError(utteranceId, errorCode)
                    }
                }

                @Deprecated("")
                override fun onError(utteranceId: String?) {
                }

                override fun onBeginSynthesis(utteranceId: String?, sampleRateInHz: Int, audioFormat: Int, channelCount: Int) {
                }

                override fun onAudioAvailable(utteranceId: String?, audio: ByteArray?) {
                }
            })
        }

        internal interface TTSListener {
            fun onStart(utteranceId: String?)
            fun onDone(utteranceId: String?)
            fun onStop(utteranceId: String?, interrupted: Boolean)
            fun onError(utteranceId: String?, errorCode: Int)
            fun onReadFailed(isTimeShort: Boolean)
        }
    }
}
