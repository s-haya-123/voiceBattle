package voicebattle.com.shaya.voicebattle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var audioSample:AudioSample
    lateinit var graphSample:GraphSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recognizer = audioSample.getRecognition(this)
        record.setOnClickListener{
            audioSample.startAudioRecord()
        }
        stop.setOnClickListener {
            audioSample.stopAudioRecord()
        }
        play.setOnClickListener {
            audioSample.playAudioRecord()
        }

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
