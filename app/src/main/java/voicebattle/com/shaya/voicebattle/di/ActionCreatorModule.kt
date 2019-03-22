package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.meter.AudioActionCreator
import voicebattle.com.shaya.voicebattle.Dispatcher
import voicebattle.com.shaya.voicebattle.ranking.FirebaseActionCreator
import javax.inject.Singleton

@Module
class ActionCreatorModule {
    @Provides
    @Singleton
    fun provideAudioModule(dispatcher: Dispatcher): AudioActionCreator {
        return AudioActionCreator(dispatcher)
    }
    @Provides
    @Singleton
    fun provideFirebaseActionCreator(dispatcher: Dispatcher):FirebaseActionCreator {
        return FirebaseActionCreator(dispatcher)
    }
}