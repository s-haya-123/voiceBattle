package voicebattle.com.shaya.voicebattle.di

import dagger.Component
import voicebattle.com.shaya.voicebattle.meter.MeterFragment
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            ActionCreatorModule::class,
            DispatcherModule::class
        ]
)
interface RankingComponent {
    fun inject(rankingFlagment: RankingFlagment)
}
