package voicebattle.com.shaya.voicebattle.ranking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.ranking.*
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerRankingComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import javax.inject.Inject

class RankingFlagment : Fragment() {
    @Inject
    lateinit var actionCreator:FirebaseActionCreator
    @Inject
    lateinit var store: Store

    var appComponent = DaggerRankingComponent.builder()
            .actionCreatorModule(ActionCreatorModule())
            .dispatcherModule(DispatcherModule())
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ranking,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        store.entitiesOrderByPower.subscribe {
            it.forEachIndexed { index, rankingEntity ->
                val ranking_line = layoutInflater.inflate(R.layout.ranking_line,null).apply {
                    findViewById<TextView>(R.id.rank_text).text = (index+1).toString()
                    findViewById<TextView>(R.id.name_text).text = rankingEntity.name
                    findViewById<TextView>(R.id.power_text).text = rankingEntity.power.toString()
                }
                ranking_list.addView(ranking_line)
            }
        }

        actionCreator.getRanking()
        actionCreator.setRanking(RankingEntity("test",8000))
    }

    companion object {
        fun newInstance()  = RankingFlagment()
    }
}