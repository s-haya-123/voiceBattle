package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.meter.AudioActionCreator
import voicebattle.com.shaya.voicebattle.meter.AudioController

@Module
class AudioControllerModule {
    @Provides
    fun provideAudioController(audioActionCreator: AudioActionCreator): AudioController {
        return AudioController(audioActionCreator)
    }
}