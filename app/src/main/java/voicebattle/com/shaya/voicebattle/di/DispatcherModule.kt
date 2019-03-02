package voicebattle.com.shaya.voicebattle.di

import dagger.Module
import dagger.Provides
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Singleton

@Module
class DispatcherModule {
    @Provides
    @Singleton
    fun provideDispatcher():Dispatcher{
        return Dispatcher()
    }
}