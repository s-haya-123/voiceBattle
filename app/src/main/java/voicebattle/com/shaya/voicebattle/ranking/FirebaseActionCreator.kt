package voicebattle.com.shaya.voicebattle.ranking

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class FirebaseActionCreator @Inject constructor(val dispatcher: Dispatcher){
    fun getRanking(){
        val db = FirebaseFirestore.getInstance()
        db.collection("ranking")
                .orderBy("power")
                .get()
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        val entities = task.getResult()
                                .filter { it.contains("power") and it.contains("name")}
                                .map { value ->
                                    RankingEntity(value.data.get("name") as String,value.data.get("power") as Long)
                                }
                        dispatcher.dispatch(FirebaseAction.Ranking(entities))
                    } else {
                        Log.d("fbLog",task.exception.toString())
                    }
                }
    }
}