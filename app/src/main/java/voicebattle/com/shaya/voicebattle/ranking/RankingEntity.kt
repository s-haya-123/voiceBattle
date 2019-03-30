package voicebattle.com.shaya.voicebattle.ranking

class RankingEntity(
        var name:String,
        var power:Long,
        var id:String?) {

    companion object {
        val POWER = "power"
        val NAME = "name"
    }
}