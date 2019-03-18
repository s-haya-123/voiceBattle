package voicebattle.com.shaya.voicebattle.ranking

import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class FirebaseStore @Inject constructor(val dispatcher: Dispatcher) {
    var test = dispatcher.on(FirebaseAction.Test::class.java)
            .map { it.test }
}