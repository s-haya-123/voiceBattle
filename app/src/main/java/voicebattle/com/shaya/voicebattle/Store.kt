package voicebattle.com.shaya.voicebattle

import voicebattle.com.shaya.voicebattle.meter.AudioAction
import voicebattle.com.shaya.voicebattle.ranking.FirebaseAction
import javax.inject.Inject

class Store @Inject constructor(val dispatcher: Dispatcher) {
    var test = dispatcher.on(FirebaseAction.Test::class.java)
            .map { it.test }
    var entities = dispatcher.on(FirebaseAction.Ranking::class.java)
            .map { it.enitities }
    val refreshValume = dispatcher
            .on(AudioAction.AudioValume::class.java)
            .map{it.volume}
}