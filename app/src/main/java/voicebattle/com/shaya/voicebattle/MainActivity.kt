package voicebattle.com.shaya.voicebattle

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import kotlinx.android.synthetic.main.battle_layout.*


class MainActivity : AppCompatActivity() {
    lateinit var audioSample:AudioSample
    lateinit var graphSample:GraphSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_layout)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // RECORD_AUDIO の実行時パーミッションを要求
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
        calculate_start.setOnClickListener {
           ValueAnimator().apply {
                setIntValues(0,100)
                addUpdateListener {anim:ValueAnimator->
                    battleValue.text = anim.animatedValue.toString()
                }
                setDuration(300)
                start()
            }

        }

    }

}
