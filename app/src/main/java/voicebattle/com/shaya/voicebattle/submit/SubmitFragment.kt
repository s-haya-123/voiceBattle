package voicebattle.com.shaya.voicebattle.submit

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.submit_layout.*
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
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
        return inflater.inflate(R.layout.submit_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getString(SubmitFragment.KEY).let {
                ValueAnimator.ofInt(0,it.toInt()).apply {
                    duration = 1000
                    addUpdateListener {
                        power_result.text = it.getAnimatedValue().toString()
                    }
                    addListener(object:AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            power_result.setTextColor(Color.RED)
                        }
                    })
                    start()
                }
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
            Navigation.findNavController(it).navigate(R.id.action_submitFragment_to_rankingFlagment,bundle)
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