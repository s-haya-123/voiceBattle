package voicebattle.com.shaya.voicebattle.meter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import voicebattle.com.shaya.voicebattle.submit.SubmitFragment
import javax.inject.Inject

class MeterFragment : Fragment(){
    @Inject
    lateinit var audioStore: Store
    @Inject
    lateinit var audioController: AudioController
    @Inject
    lateinit var audioActionCreator: AudioActionCreator
    val compositeDisposable = CompositeDisposable()
    var valueArray:MutableList<Int> = mutableListOf<Int>()
    val TimeLimitCount = 1000
    val intervalMs:Long = 5

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
            view.visibility = View.INVISIBLE
            GlobalScope.launch {
                (0..TimeLimitCount).forEach {
                    Log.d("test",(( it / TimeLimitCount.toFloat())).toString())
                    audioActionCreator.updateRemainingTime((1 - it / TimeLimitCount.toFloat()) * 100)
                    Thread.sleep(intervalMs)
                }
                activity?.let {
                    val bundle = Bundle().apply {
                        putString(SubmitFragment.KEY,calcTotalValue(valueArray).toString())
                    }
                    Navigation.findNavController(view).navigate(R.id.action_meterFragment_to_submitFragment,bundle)
                }
            }
        }
        audioStore.refreshValume.subscribe{
            valueArray.add(it)
        }.apply {
            compositeDisposable.add(this)
        }
    }
    private fun calcTotalValue(values:List<Int>):Int{
        return values.average().toInt()
    }

    override fun onDestroyView() {
        audioController.stopRecord()
        compositeDisposable.clear()
        super.onDestroyView()
    }

}