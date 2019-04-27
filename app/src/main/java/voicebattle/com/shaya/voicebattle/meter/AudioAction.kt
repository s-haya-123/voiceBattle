package voicebattle.com.shaya.voicebattle.meter

import voicebattle.com.shaya.voicebattle.Action

sealed class AudioAction: Action {
    class AudioValume(val volume: Int) : AudioAction()
    class RemainingTime(val remainingTime:Float) : AudioAction()
}