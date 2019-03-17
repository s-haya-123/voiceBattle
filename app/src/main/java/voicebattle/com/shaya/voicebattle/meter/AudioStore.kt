package voicebattle.com.shaya.voicebattle.meter

import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class AudioStore @Inject constructor( val dispatcher: Dispatcher) {
    val refreshValume = dispatcher
            .on(AudioAction.AudioValume::class.java)
            .map{it.volume}

}