package voicebattle.com.shaya.voicebattle.ranking

import voicebattle.com.shaya.voicebattle.Dispatcher
import javax.inject.Inject

class FirebaseActionCreator @Inject constructor(val dispatcher: Dispatcher){
    fun getRanking(){

    }
    fun test() = dispatcher.dispatch(FirebaseAction.Test("test"))
}