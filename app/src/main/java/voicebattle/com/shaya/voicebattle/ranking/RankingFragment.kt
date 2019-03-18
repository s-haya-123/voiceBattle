package voicebattle.com.shaya.voicebattle.ranking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.ranking.*
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerMeterComponent
import voicebattle.com.shaya.voicebattle.di.DaggerRankingComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import javax.inject.Inject

class RankingFlagment : Fragment() {
    @Inject
    lateinit var actionCreator:FirebaseActionCreator
    @Inject
    lateinit var store:FirebaseStore

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
        store.entities.subscribe {
            it.forEach {
                val ranking_line = layoutInflater.inflate(R.layout.ranking_line,null).apply {
                    findViewById<TextView>(R.id.rank_text).text = (it.ranking+1).toString()
                    findViewById<TextView>(R.id.name_text).text = it.name
                    findViewById<TextView>(R.id.power_text).text = it.power.toString()
                }
                ranking_list.addView(ranking_line)
            }
        }

        actionCreator.getRanking()
    }

    companion object {
        fun newInstance()  = RankingFlagment()
    }
}