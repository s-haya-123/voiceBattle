package voicebattle.com.shaya.voicebattle.di

import dagger.Component
import voicebattle.com.shaya.voicebattle.meter.MeterFragment
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            ActionCreatorModule::class,
            DispatcherModule::class,
            AudioControllerModule::class
        ]
)
interface MeterComponent {
    fun inject(meterFragment: MeterFragment)
}
