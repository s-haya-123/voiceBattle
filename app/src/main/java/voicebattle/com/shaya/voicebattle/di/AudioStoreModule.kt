package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.meter.AudioStore
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Singleton

@Module
class AudioStoreModule {
    @Provides
    @Singleton
    fun provideStore(dispatcher: Dispatcher): AudioStore {
        return AudioStore(dispatcher)
    }
}