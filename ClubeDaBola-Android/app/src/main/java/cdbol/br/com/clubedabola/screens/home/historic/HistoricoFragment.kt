package cdbol.br.com.clubedabola.screens.home.historic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.screens.home.FragmentAbstrato
import cdbol.br.com.clubedabola.screens.home.HomeContract
import cdbol.br.com.clubedabola.screens.rating.RatingHirerActivity
import cdbol.br.com.clubedabola.screens.rating.goalkeeper.RatingGoalkeeperActivity
import cdbol.br.com.clubedabola.utils.PreferencesString
import kotlinx.android.synthetic.main.fragment_historico.*
import org.jetbrains.anko.support.v4.intentFor


class HistoricoFragment : FragmentAbstrato(), HistoricAdapter.ClickListener {

    companion object {
        @JvmField
        val TITULO = "Histórico"
    }

    var adapter : HistoricAdapter? = null
    var mView : HomeContract.View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_historico, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = activity as HomeContract.View
        rl_historic.layoutManager = LinearLayoutManager(activity)
        adapter = HistoricAdapter(this)
        rl_historic.adapter = adapter
        rl_historic.isNestedScrollingEnabled = false

        callIHiredPassed()

        radio_group_historic.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_i_hired_historic -> callIHiredPassed()
                R.id.radio_button_was_hired_historic -> callWasHiredPassed()
            }
        }


        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false

            }
        })
    }

    var type:Int = 0
    override fun onClickToRating(id: String) {
        if(type == 0) startActivity(intentFor<RatingGoalkeeperActivity>("id" to id))
        else startActivity(intentFor<RatingHirerActivity>("id" to id))
    }


    private fun callIHiredPassed(){
        mView!!.showFullProgressbar()
        adapter!!.notifyAdapter(null, 1)
        ApiManager.getPassedMatch(PreferencesString.instance.getString("hirerId")!!)
                .subscribe ({
                    mView!!.hideFullProgressbar()
                    adapter!!.notifyAdapter(it, 0)
                    type = 0
                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Erro", it.message)
                })
    }

    private fun callWasHiredPassed(){
        mView!!.showFullProgressbar()
        adapter!!.notifyAdapter(null, 1)
        ApiManager.getPassedHirerMatch(PreferencesString.instance.getString("hirerId")!!)
                .subscribe ({
                    mView!!.hideFullProgressbar()
                    adapter!!.notifyAdapter(it, 1)
                    type = 1
                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Erro Historico", it.message)})
    }

}
