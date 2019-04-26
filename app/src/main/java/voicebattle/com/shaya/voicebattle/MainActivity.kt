package voicebattle.com.shaya.voicebattle

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import voicebattle.com.shaya.voicebattle.meter.MeterFragment
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import voicebattle.com.shaya.voicebattle.submit.SubmitFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPermission()
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    private fun setPermission(){
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }
    override fun onSupportNavigateUp() = findNavController(R.id.submitFragment).navigateUp()


}
