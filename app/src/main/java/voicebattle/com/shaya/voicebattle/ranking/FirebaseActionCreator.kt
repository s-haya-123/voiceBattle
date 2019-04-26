package voicebattle.com.shaya.voicebattle.ranking

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import voicebattle.com.shaya.voicebattle.Dispatcher
import java.util.*
import javax.inject.Inject
import com.google.firebase.firestore.DocumentReference



class FirebaseActionCreator @Inject constructor(val dispatcher: Dispatcher){
    val COLLECTION_NAME = "ranking"


    fun getRanking(){
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME)
                .orderBy(RankingEntity.POWER,Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        val entities = task.getResult()
                                ?.filter { it.contains(RankingEntity.POWER) and it.contains(RankingEntity.NAME)}
                                ?.map { value ->
                                    RankingEntity(value.data.get(RankingEntity.NAME) as String,value.data.get(RankingEntity.POWER) as Long,value.id)
                                }
                        if(entities != null) {
                            dispatcher.dispatch(FirebaseAction.Ranking(entities))
                        } else {
                            Log.d("fbLog",task.exception.toString())
                        }

                    } else {
                        Log.d("fbLog",task.exception.toString())
                    }
                }
    }
    fun setRanking(entity: RankingEntity){
        val data = HashMap<String,Any>().apply {
            put(RankingEntity.POWER,entity.power)
            put(RankingEntity.NAME,entity.name)
        }
        val db = FirebaseFirestore.getInstance()
        val newCityRef = db.collection(COLLECTION_NAME).document()
        newCityRef.set(data)
                .addOnSuccessListener {
                    dispatcher.dispatch(FirebaseAction.SetRankingSucess(newCityRef.id))
                }
                .addOnFailureListener {
                    dispatcher.dispatch((FirebaseAction.SetRankingFailure()))
                }
    }
}