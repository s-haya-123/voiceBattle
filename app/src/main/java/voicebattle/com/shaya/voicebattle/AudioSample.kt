package voicebattle.com.shaya.voicebattle

import android.content.Intent
import android.media.*
import android.util.Log
import kotlin.math.max
import java.nio.file.Files.createFile
import android.media.AudioFormat.CHANNEL_OUT_STEREO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioAttributes
import android.media.AudioTrack
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


/**
 * AudioRecord クラスのサンプルコード
 */
class AudioSample(val graphSample: GraphSample) {

    var audioRecord: AudioRecord //録音用のオーディオレコードクラス
    val SAMPLING_RATE = 44100 //オーディオレコード用サンプリング周波数
    private var bufSize: Int//オーディオレコード用バッファのサイズ
    private var shortData: ShortArray //オーディオレコード用バッファ
    private  var playShortData:ShortArray
    private val loopcnt = 500
    private  val framerate = 2
    private val oneFrameDataCount:Int  //SAMPLING_RATE / framerate
//    private val oneFrameSizeInByte = oneFrameDataCount * 2

    //AudioRecordの初期化
    init {
        // AudioRecordオブジェクトを作成
        bufSize = max(1 * 10,AudioRecord.getMinBufferSize(SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT))
        oneFrameDataCount = bufSize/2
        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLING_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufSize)

        shortData = ShortArray(oneFrameDataCount)
        playShortData = ShortArray(oneFrameDataCount * loopcnt)
        var cnt =0
        // コールバックを指定
        audioRecord.setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
            // フレームごとの処理
            override fun onPeriodicNotification(recorder: AudioRecord) {
                val max = shortData.max()!!.toFloat()
                val min = shortData.min()!!.toFloat()
                val values = if(abs(max) > abs(min)) max else min
                graphSample.addGraph(values)
                System.arraycopy(shortData,0,playShortData,cnt * oneFrameDataCount,oneFrameDataCount)
                audioRecord.read(shortData, 0, oneFrameDataCount) // 読み込む
                cnt++
                Log.d("sound",cnt.toString())
            }

            override fun onMarkerReached(recorder: AudioRecord) {
                Log.d("sound","marker")

            }
        })
        // コールバックが呼ばれる間隔を指定
        audioRecord.positionNotificationPeriod = oneFrameDataCount
        Log.d("sound","${AudioRecord.getMinBufferSize(SAMPLING_RATE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)}, ${oneFrameDataCount}")
    }

    fun getRecognition(context:MainActivity):SpeechRecognizer{
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName())
        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                Toast.makeText(context, "音声認識準備完了", Toast.LENGTH_SHORT)
            }

            // 音声入力開始
            override fun onBeginningOfSpeech() {
                Toast.makeText(context, "入力開始", Toast.LENGTH_SHORT)
            }

            // 録音データのフィードバック用
            override fun onBufferReceived(buffer: ByteArray) {
                Log.v("tag", "onBufferReceived")
            }

            // 入力音声のdBが変化した
            override fun onRmsChanged(rmsdB: Float) {
                Log.v("tag", "recieve : " + rmsdB + "dB")
            }

            // 音声入力終了
            override fun onEndOfSpeech() {
                Toast.makeText(context, "入力終了", Toast.LENGTH_SHORT)
            }

            // ネットワークエラー又は、音声認識エラー
            override fun onError(error: Int) {

            }

            // イベント発生時に呼び出される
            override fun onEvent(eventType: Int, params: Bundle) {
                Log.v("tag", "onEvent")
            }

            // 部分的な認識結果が得られる場合に呼び出される
            override fun onPartialResults(partialResults: Bundle) {
                Log.d("tag", "onPartialResults")
            }

            override fun onResults(results: Bundle) {
                val recData = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
                Toast.makeText(context,recData.toString(), Toast.LENGTH_LONG)
            }
        })
        return recognizer
    }
    fun playAudioRecord(){
        val player = AudioTrack.Builder()
                .setAudioAttributes(AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .setAudioFormat(AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLING_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setTransferMode(AudioTrack.MODE_STREAM)
                .setBufferSizeInBytes(bufSize/framerate)
                .build()
//        player.reloadStaticData()
        player.setPlaybackRate(SAMPLING_RATE)
        player.play()
        player.write(playShortData, 0, playShortData.size)
        player.stop()
        player.flush()
    }


    fun startAudioRecord() {
        audioRecord.startRecording()
        audioRecord.read(shortData, 0, oneFrameDataCount)
//        (0..loopcnt-1).forEach {
//            audioRecord.read(shortData!!, 0, bufSize / 2)
//            System.arraycopy(shortData,0,playShortData,it * bufSize / 2,bufSize / 2)
//            Log.d("sound",shortData!!.map { abs(it.toInt()) }.max().toString())
//        }
//
//        audioRecord.stop()
        Log.d("sound","end.")
    }

    //オーディオレコードを停止する
    fun stopAudioRecord() {
        audioRecord.stop()
    }
}