package voicebattle.com.shaya.voicebattle.meter

import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class AudioActionCreator @Inject constructor(private val dispatcher: Dispatcher) {
    fun test(){
        dispatcher.dispatch(AudioAction.AudioValume(10))
    }
    fun updateMicVolume(volume:Int){
        dispatcher.dispatch(AudioAction.AudioValume(volume))
    }
}