package voicebattle.com.shaya.voicebattle.di

import dagger.Component
import voicebattle.com.shaya.voicebattle.MainActivity
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
        AudioModule::class
        ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
