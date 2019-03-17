package voicebattle.com.shaya.voicebattle.di

import dagger.Component
import voicebattle.com.shaya.voicebattle.MainActivity
import voicebattle.com.shaya.voicebattle.MeterFragment
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AudioActionCreatorModule::class,
            DispatcherModule::class
        ]
)
interface AppComponent {
    fun inject(meterFragment: MeterFragment)
}
