package voicebattle.com.shaya.voicebattle.ranking

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject



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
                            dispatcher.dispatch((FirebaseAction.SetRankingFailure()))
                        }

                    } else {
                        dispatcher.dispatch((FirebaseAction.SetRankingFailure()))
                    }
                }
    }
    fun setRanking(entity: RankingEntity){
        val data = HashMap<String,Any>().apply {
            put(RankingEntity.POWER,entity.power)
            put(RankingEntity.NAME,entity.name)
        }
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection(COLLECTION_NAME).document()
        collection.set(data)
                .addOnSuccessListener {
                    dispatcher.dispatch(FirebaseAction.SetRankingSucess(collection.id))
                }
                .addOnFailureListener {
                    dispatcher.dispatch((FirebaseAction.SetRankingFailure()))
                }
    }
}