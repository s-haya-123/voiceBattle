package voicebattle.com.shaya.voicebattle.meter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.battle_layout.*
import voicebattle.com.shaya.voicebattle.MainActivity
import voicebattle.com.shaya.voicebattle.R
import voicebattle.com.shaya.voicebattle.Store
import voicebattle.com.shaya.voicebattle.di.ActionCreatorModule
import voicebattle.com.shaya.voicebattle.di.DaggerMeterComponent
import voicebattle.com.shaya.voicebattle.di.DispatcherModule
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

    }
    companion object {
        fun newInstance(): MeterFragment = MeterFragment()
    }
}