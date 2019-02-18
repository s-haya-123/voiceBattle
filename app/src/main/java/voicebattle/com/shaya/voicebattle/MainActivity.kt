package voicebattle.com.shaya.voicebattle

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.XAxis
import android.view.View
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import android.graphics.DashPathEffect
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.RecognizerIntent
import android.content.Intent
import android.widget.Toast
import android.system.Os.listen
import android.util.Log


class MainActivity : AppCompatActivity() {
    lateinit var audioSample:AudioSample
    lateinit var graphSample:GraphSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        record.setOnClickListener{
            audioSample.startAudioRecord()
        }
        stop.setOnClickListener {
            audioSample.stopAudioRecord()
        }
        play.setOnClickListener {
            audioSample.playAudioRecord()
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName())
        val recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                Toast.makeText(this@MainActivity, "音声認識準備完了", Toast.LENGTH_SHORT)
            }

            // 音声入力開始
            override fun onBeginningOfSpeech() {
                Toast.makeText(this@MainActivity, "入力開始", Toast.LENGTH_SHORT)
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
                Toast.makeText(this@MainActivity, "入力終了", Toast.LENGTH_SHORT)
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
                Toast.makeText(this@MainActivity,recData.toString(),Toast.LENGTH_LONG)
                recognitionText.text = recData.toString()
            }
        })
        recognition.setOnClickListener {
            recognizer.startListening(intent)
        }
        graphSample = GraphSample(this)
        graphSample.setGraph()
        audioSample= AudioSample(graphSample)
//
//        graphSample.addGraph(shortArrayOf(1,2,3))
    }

}
