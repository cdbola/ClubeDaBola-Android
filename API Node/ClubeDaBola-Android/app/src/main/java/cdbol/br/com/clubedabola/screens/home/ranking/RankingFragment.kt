package cdbol.br.com.clubedabola.screens.home.ranking

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.screens.home.FragmentAbstrato
import cdbol.br.com.clubedabola.screens.rating.RatingHirerActivity
import kotlinx.android.synthetic.main.fragment_ranking.*

class RankingFragment : FragmentAbstrato() {

    companion object {
        @JvmField
        val TITULO = "Ranque"
    }

    var adapter : RankingAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ranking, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rl_ranking.layoutManager = LinearLayoutManager(activity)

        adapter = RankingAdapter()
        rl_ranking.adapter = adapter

        callGoalkeeper()

        radio_group_ranking.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_goalkeeper -> callGoalkeeper()
                R.id.radio_button_referee-> callReferee()
            }
        }
    }

    private fun callGoalkeeper(){
        adapter!!.notifyAdapter(null)

        ApiManager.getRankingGoalkeeper()
                .subscribe ({
                    for (item in it) {
                        item.orderNum += 1
                    }
                    adapter!!.notifyAdapter(it)
                }, { Log.e("Erro", "Ranking Goleiro")})
    }

    private fun callReferee(){
        adapter!!.notifyAdapter(null)
        ApiManager.getRankingReferee()
                .subscribe ({
                    for (item in it) {
                        item.orderNum += 1
                    }
                    adapter!!.notifyAdapter(it)
                }, {Log.e("Erro", "Ranking Arbitro")})
    }
}