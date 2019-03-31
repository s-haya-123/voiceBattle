package voicebattle.com.shaya.voicebattle.submit

import android.os.Bundle
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.ranking.*
import kotlinx.android.synthetic.main.result_layout.*
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerRankingComponent
import voicebattle.com.shaya.voicebattle.di.DaggerSubmitComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import voicebattle.com.shaya.voicebattle.ranking.FirebaseActionCreator
import voicebattle.com.shaya.voicebattle.ranking.RankingEntity
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import javax.inject.Inject

class SubmitFragment : Fragment() {
    @Inject lateinit var actionCreator:FirebaseActionCreator
    @Inject lateinit var store: Store
    val compositeDisposable = CompositeDisposable()


    var appComponent = DaggerSubmitComponent.builder()
            .actionCreatorModule(ActionCreatorModule())
            .dispatcherModule(DispatcherModule())
            .build()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.result_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getString(SubmitFragment.KEY).let {
                power_result.text = it
            }
        }
        appComponent.inject(this)
        submit.setOnClickListener {
            val power = power_result.text.toString().toLong()
            val name = ranking_name.text.toString()
            actionCreator.setRanking(RankingEntity(name,power,null))
        }
        store.FirebaseState.subscribe {
            moveRankingFragment(it.id)
        }.apply {
            compositeDisposable.add(this)
        }


    }
    private fun moveRankingFragment(id:String){
        view?.let {
            val bundle = Bundle().apply {
                putString(RankingFlagment.KEY,id)
            }
            Navigation.findNavController(it).navigate(R.id.rankingFlagment,bundle)
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        val KEY = "POWER"
    }
}