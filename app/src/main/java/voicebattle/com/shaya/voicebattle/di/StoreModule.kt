package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.meter.AudioStore
import voicebattle.com.shaya.voicebattle.Dispatcher
import voicebattle.com.shaya.voicebattle.ranking.FirebaseStore
import javax.inject.Singleton

@Module
class StoreModule {
    @Provides
    @Singleton
    fun provideStore(dispatcher: Dispatcher): AudioStore {
        return AudioStore(dispatcher)
    }

    @Provides
    @Singleton
    fun provideFirebaseStore(dispatcher: Dispatcher): FirebaseStore {
        return FirebaseStore(dispatcher)
    }
}