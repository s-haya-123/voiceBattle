package voicebattle.com.shaya.voicebattle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val audioSample:AudioSample = AudioSample()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        record.setOnClickListener{
            audioSample.startAudioRecord()
        }
        stop.setOnClickListener {
            audioSample.stopAudioRecord()
        }

    }
}
