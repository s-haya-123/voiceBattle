package voicebattle.com.shaya.voicebattle.ranking

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.ranking.*
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerRankingComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import javax.inject.Inject

class RankingFlagment :  Fragment() {
    @Inject
    lateinit var actionCreator:FirebaseActionCreator
    @Inject
    lateinit var store: Store
    val compositeDisposable = CompositeDisposable()
    lateinit var id:String

    var appComponent = DaggerRankingComponent.builder()
            .actionCreatorModule(ActionCreatorModule())
            .dispatcherModule(DispatcherModule())
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ranking,container,false).apply {
            isFocusableInTouchMode = true
            requestFocus()
            activity?.let {
                setOnKeyListener { view, keyCode, keyEvent ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        Navigation.findNavController(view).navigate(R.id.action_rankingFlagment_to_meterFragment)
                        true
                    }
                    false
                }
            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getString(RankingFlagment.KEY)?.let {
                id = it
            }
        }
        appComponent.inject(this)
        store.entitiesOrderByPower.subscribe {
            it.forEachIndexed { index, rankingEntity ->
                layoutInflater.inflate(R.layout.ranking_line,null).apply {
                    if( rankingEntity.id == this@RankingFlagment.id){
                        setBackgroundColor(Color.CYAN)
                        rank_number.text = "${index+1}位"
                    }
                    findViewById<TextView>(R.id.rank_text)?.apply {
                        text = (index+1).toString()
                        gravity = Gravity.LEFT
                    }
                    findViewById<TextView>(R.id.name_text).text = rankingEntity.name
                    findViewById<TextView>(R.id.power_text)?.apply {
                        text = rankingEntity.power.toString()
                        gravity = Gravity.RIGHT
                    }

                    ranking_list.addView(this)
                }
            }
        }.apply {
            compositeDisposable.add(this)
        }
        actionCreator.getRanking()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        val KEY="FIREBASE_ID"
    }
}