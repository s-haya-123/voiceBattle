package voicebattle.com.shaya.voicebattle

import android.arch.lifecycle.LiveData
import android.util.Log
import javax.inject.Inject

class AudioStore @Inject constructor( val dispatcher: Dispatcher) {
    val refreshValume = dispatcher
            .on(AudioAction.AudioValume::class.java)
            .map{it.volume}

}