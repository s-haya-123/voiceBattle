package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.AudioActionCreator
import javax.inject.Singleton

@Module
class AudioModule {
    @Provides
    @Singleton
    fun provideAudioActionCreator(): AudioActionCreator {
        return AudioActionCreator()
    }
}