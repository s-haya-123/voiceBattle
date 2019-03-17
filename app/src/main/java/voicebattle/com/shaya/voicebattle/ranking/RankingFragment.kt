package voicebattle.com.shaya.voicebattle.ranking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import voicebattle.com.shaya.voicebattle.R

class RankingFlagment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getRankingFromFirebase()
        return inflater.inflate(R.layout.ranking,container,false)
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