package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.AudioActionCreator
import voicebattle.com.shaya.voicebattle.AudioController
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Singleton

@Module
class AudioControllerModule {
    @Provides
    fun provideAudioController(audioActionCreator: AudioActionCreator): AudioController {
        return AudioController(audioActionCreator)
    }
}