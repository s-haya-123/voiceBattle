package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.Dispatcher
import voicebattle.com.shaya.voicebattle.Store
import javax.inject.Singleton

@Module
class StoreModule {
    @Provides
    @Singleton
    fun provideFirebaseStore(dispatcher: Dispatcher): Store {
        return Store(dispatcher)
    }
}