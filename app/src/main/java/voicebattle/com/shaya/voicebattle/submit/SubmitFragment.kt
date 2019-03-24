package voicebattle.com.shaya.voicebattle.submit

import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            actionCreator.setRanking(RankingEntity(name,power))
        }
        store.FirebaseState.subscribe {
            moveRankingFragment()
        }.apply {
            compositeDisposable.add(this)
        }

    }
    private fun moveRankingFragment(){
        activity?.let {
            val fragmentManager: FragmentManager = it.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.activity_main,RankingFlagment.newInstance())
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        val KEY = "POWER"
        fun newInstance(power:String)  = SubmitFragment().apply {
            arguments = Bundle().apply {
                putString(KEY,power)
            }
        }
    }
}