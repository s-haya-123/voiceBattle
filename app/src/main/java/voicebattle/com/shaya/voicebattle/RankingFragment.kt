package voicebattle.com.shaya.voicebattle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RankingFlagment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ranking,container,false)
    }
    companion object {
        fun newInstance()  = RankingFlagment()
    }
}