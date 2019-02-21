package voicebattle.com.shaya.voicebattle

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.battle_layout.*


class MainActivity : AppCompatActivity() {
    lateinit var audioSample:AudioSample
    lateinit var graphSample:GraphSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_layout)


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
