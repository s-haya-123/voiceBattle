package voicebattle.com.shaya.voicebattle

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.battle_layout.*
import voicebattle.com.shaya.voicebattle.di.AudioModule
import voicebattle.com.shaya.voicebattle.di.DaggerAppComponent
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject lateinit var audioActionCreator: AudioActionCreator

    val appComponent = DaggerAppComponent.builder().audioModule(AudioModule()).build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_layout)
        appComponent.inject(this)

        setPermission()
        calculate_start.setOnClickListener {
            audioActionCreator.test()
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
    private fun setPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }


}
