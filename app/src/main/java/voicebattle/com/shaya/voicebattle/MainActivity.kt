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




class MainActivity : AppCompatActivity() {
    val audioSample:AudioSample = AudioSample()
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
        graphSample = GraphSample(this)
        graphSample.setGraph()
        graphSample.addGraph(130f)
        graphSample.addGraph(150f)
    }

}
