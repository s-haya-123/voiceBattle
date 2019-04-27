package voicebattle.com.shaya.voicebattle

import voicebattle.com.shaya.voicebattle.meter.AudioAction
import voicebattle.com.shaya.voicebattle.ranking.FirebaseAction
import javax.inject.Inject

class Store @Inject constructor(val dispatcher: Dispatcher) {
    init {
        dispatcher
                .on(AudioAction.RemainingTime::class.java)
                .map { it.remainingTime }
                .subscribe{ remainingTimePercent = it }
    }
    var remainingTimePercent:Float = 100f
    var entitiesOrderByPower = dispatcher.on(FirebaseAction.Ranking::class.java)
            .map { it.enitities }
    val refreshValume = dispatcher
            .on(AudioAction.AudioValume::class.java)
            .map{it.volume}
    val FirebaseState = dispatcher.on(FirebaseAction.SetRankingSucess::class.java)
}