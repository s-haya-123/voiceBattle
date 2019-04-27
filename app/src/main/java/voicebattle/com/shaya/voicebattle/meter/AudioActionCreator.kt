package voicebattle.com.shaya.voicebattle.meter

import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class AudioActionCreator @Inject constructor(private val dispatcher: Dispatcher) {
    fun updateMicVolume(volume:Int){
        dispatcher.dispatch(AudioAction.AudioValume(volume))
    }
    fun updateRemainingTime(remainingTime: Float){
        dispatcher.dispatch(AudioAction.RemainingTime(remainingTime))
    }
}