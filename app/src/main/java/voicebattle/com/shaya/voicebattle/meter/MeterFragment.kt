package voicebattle.com.shaya.voicebattle.meter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.reactivex.disposables.CompositeDisposable
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

class MeterFragment : Fragment(){
    @Inject
    lateinit var audioStore: Store
    @Inject
    lateinit var audioController: AudioController
    val compositeDisposable = CompositeDisposable()

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

        calculate_start.setOnClickListener {view ->
            audioController.startRecord()
            GlobalScope.launch {
                Thread.sleep(5000)
                activity?.let {
                    val bundle = Bundle().apply {
                        putString(SubmitFragment.KEY,battleValue.text.toString())
                    }
                    Navigation.findNavController(view).navigate(R.id.action_meterFragment_to_submitFragment,bundle)
                }
            }
        }
        audioStore.refreshValume.subscribe{
            battleValue.text = it.toString()
        }.apply {
            compositeDisposable.add(this)
        }
    }

    override fun onDestroyView() {
        audioController.stopRecord()
        compositeDisposable.clear()
        super.onDestroyView()
    }

}