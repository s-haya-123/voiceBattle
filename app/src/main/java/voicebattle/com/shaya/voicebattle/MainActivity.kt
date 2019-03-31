package voicebattle.com.shaya.voicebattle

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.content.ContextCompat
import voicebattle.com.shaya.voicebattle.meter.MeterFragment
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import voicebattle.com.shaya.voicebattle.submit.SubmitFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermission()

        if(savedInstanceState == null){
            val fragmentManager: FragmentManager = this.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.add(R.id.activity_main, MeterFragment.newInstance(),"ScheduleFlagment")
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
