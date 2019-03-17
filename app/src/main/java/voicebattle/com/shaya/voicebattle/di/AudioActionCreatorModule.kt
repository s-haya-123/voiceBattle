package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.meter.AudioActionCreator
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Singleton

@Module
class AudioActionCreatorModule {
    @Provides
    @Singleton
    fun provideAudioModule(dispatcher: Dispatcher): AudioActionCreator {
        return AudioActionCreator(dispatcher)
    }
}