package voicebattle.com.shaya.voicebattle.meter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.battle_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import voicebattle.com.shaya.voicebattle.MainActivity
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerMeterComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
import voicebattle.com.shaya.voicebattle.ranking.RankingFlagment
import voicebattle.com.shaya.voicebattle.submit.SubmitFragment
import javax.inject.Inject

class MeterFragment :Fragment(){
    @Inject
    lateinit var audioStore: Store
    @Inject
    lateinit var audioController: AudioController

    val appComponent = DaggerMeterComponent.builder()
            .actionCreatorModule(ActionCreatorModule())
            .dispatcherModule(DispatcherModule())
            .build()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.battle_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)

        if(activity is MainActivity){
            MeterSurface(activity as MainActivity, audioStore).apply {
                mainLayout.addView(this)
            }
        }

        calculate_start.setOnClickListener {
            audioController.startRecord()
        }
        audioStore.refreshValume.subscribe{
            battleValue.text = it.toString()
        }
        GlobalScope.launch {
            Thread.sleep(50000)
            audioController.stopRecord()
            activity?.let {
                val fragmentManager: FragmentManager = it.supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.replace(R.id.activity_main, SubmitFragment.newInstance())
                fragmentTransaction.commit()
            }
        }

    }
    companion object {
        fun newInstance(): MeterFragment = MeterFragment()
    }
}