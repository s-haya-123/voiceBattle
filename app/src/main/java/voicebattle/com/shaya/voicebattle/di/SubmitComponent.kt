package voicebattle.com.shaya.voicebattle.di

import dagger.Component
import voicebattle.com.shaya.voicebattle.meter.MeterFragment
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import voicebattle.com.shaya.voicebattle.submit.SubmitFragment
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            ActionCreatorModule::class,
            DispatcherModule::class
        ]
)
interface SubmitComponent {
    fun inject(submitFragment: SubmitFragment)
}
