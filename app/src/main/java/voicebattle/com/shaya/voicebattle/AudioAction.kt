package voicebattle.com.shaya.voicebattle

sealed class AudioAction {
    class AudioValume(val volume: Int) : AudioAction()
}