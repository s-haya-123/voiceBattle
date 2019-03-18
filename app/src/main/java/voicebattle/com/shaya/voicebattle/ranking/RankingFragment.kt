package voicebattle.com.shaya.voicebattle.ranking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        getRankingFromFirebase()
        return inflater.inflate(R.layout.ranking,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        store.test.subscribe {
            rank_text.text = it
        }
        actionCreator.test()
    }
    private fun getRankingFromFirebase(){
        val db = FirebaseFirestore.getInstance()
        db.collection("ranking")
                .get()
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        task.getResult().forEach {
                            Log.d("fbLog","${it.id} ${it.data.toString()}")
                        }
                    } else {
                        Log.d("fbLog",task.exception.toString())
                    }
                }

    }
    companion object {
        fun newInstance()  = RankingFlagment()
    }
}