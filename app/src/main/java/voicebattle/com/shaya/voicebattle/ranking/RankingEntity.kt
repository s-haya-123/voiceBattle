package voicebattle.com.shaya.voicebattle.ranking

class RankingEntity(
        var name:String,
        var power:Long) {

    companion object {
        val POWER = "power"
        val NAME = "name"
    }
}