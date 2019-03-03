package voicebattle.com.shaya.voicebattle

import android.util.Log
import javax.inject.Inject

class AudioActionCreator @Inject constructor(private val dispatcher: Dispatcher) {
    fun test(){
        dispatcher.dispatch(AudioAction.AudioValume(10))
    }
}