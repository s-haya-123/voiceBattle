package voicebattle.com.shaya.voicebattle

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.battle_layout.*
import voicebattle.com.shaya.voicebattle.di.AudioActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerAppComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermission()

        if(savedInstanceState == null){
            val fragmentManager:FragmentManager = this.supportFragmentManager
            val fragmentTransaction:FragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.add(R.id.activity_main,MeterFragment.newInstance(),"ScheduleFlagment")
            fragmentTransaction.commit()
        }

    }
    private fun setPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }


}
