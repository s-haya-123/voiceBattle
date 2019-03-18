package voicebattle.com.shaya.voicebattle.ranking

import voicebattle.com.shaya.voicebattle.Action

sealed class FirebaseAction:Action {
    class Ranking(val enitities:List<RankingEntity>):FirebaseAction()
    class Test(val test:String):FirebaseAction()
}