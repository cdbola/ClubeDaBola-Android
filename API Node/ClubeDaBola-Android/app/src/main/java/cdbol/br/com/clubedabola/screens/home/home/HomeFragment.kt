package cdbol.br.com.clubedabola.screens.home.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.AttachMatchRequest
import cdbol.br.com.clubedabola.screens.contratacao.NovaContratacaoActivity
import cdbol.br.com.clubedabola.screens.home.FragmentAbstrato
import cdbol.br.com.clubedabola.screens.home.HomeActivity
import cdbol.br.com.clubedabola.screens.home.HomeContract
import cdbol.br.com.clubedabola.utils.PreferencesString
import cdbol.br.com.clubedabola.view.AlertaIos
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertTheme
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class HomeFragment : FragmentAbstrato(), HomeAdapter.ClickListener {

    companion object {
        @JvmField
        val TITULO = "Home"
    }

    var adapter : HomeAdapter? = null
    var mView : HomeContract.View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = activity as HomeContract.View
        rl_hired.layoutManager = LinearLayoutManager(activity)
        adapter = HomeAdapter(this)
        rl_hired.adapter = adapter
        callIHired()

        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_i_hired -> callIHired()
                R.id.radio_button_was_hired -> callWasHired()
            }
        }
    }

    private fun showCancelAlert(matchId: String){
        activity!!.alert("Deseja excluir essa partida") {
            yesButton {
                mView!!.showFullProgressbar()
                ApiManager.deleteMatch(matchId!!)
                    .subscribe ({
                        mView!!.hideFullProgressbar()
                        callIHired()
                    }, {
                        mView!!.hideFullProgressbar()
                        Log.d("Erro", "Recent Match")
                    })
            }
            noButton {  }
        }.show()
    }

    private fun showDetachAlert(matchId: String){
        activity!!.alert("Deseja excluir essa partida") {
            yesButton {
                mView!!.showFullProgressbar()
                ApiManager.detachMatch(matchId!!)
                        .subscribe ({
                            mView!!.hideFullProgressbar()
                            callWasHired()
                        }, { mView!!.hideFullProgressbar()
                            Log.d("Erro", "Recent Match")
                        })
            }


            noButton {  }
        }.show()
    }

    override fun onClickCancel(matchId: String) {
        Log.d("Button","Cancel")
        showCancelAlert(matchId)
    }

    override fun onClickAttach(matchId: String, contratante: String) {
        Log.d("Button","attach")
        var attach = AttachMatchRequest( contratante, matchId )
        ApiManager.postAttachMatch(attach)
                .subscribe ({
                    mView!!.hideFullProgressbar()
                    callWasHired()
                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Erro", "Recent Match")
                })

    }

    override fun onClickDetach(matchId: String) {
        Log.d("Button","Detach")
        showDetachAlert(matchId)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnContratacao.setOnClickListener {

            val alert = AlertView("Nova Contratação", "", AlertStyle.DIALOG)
            alert.setTheme(AlertTheme.LIGHT)

            alert.addAction(AlertAction("Goleiro", AlertActionStyle.NEGATIVE) { action ->
                // Action 1 callback
                val intent = Intent(activity, NovaContratacaoActivity::class.java)
                intent.putExtra("tipo", "G")
                activity?.startActivityForResult(intent, HomeActivity().REQUEST_CREATE_MATCH)
            })
            alert.addAction(AlertAction("Árbitro", AlertActionStyle.NEGATIVE) { action ->
                // Action 2 callback
                val intent = Intent(activity, NovaContratacaoActivity::class.java)
                intent.putExtra("tipo", "A")
                activity?.startActivityForResult(intent, HomeActivity().REQUEST_CREATE_MATCH)
            })

            alert.addAction(AlertAction("Cancelar", AlertActionStyle.NEGATIVE) { action ->

            })

            alert.show(activity as AppCompatActivity)


            //  showAlert()

        }

    }

    private fun callIHired(){
        mView!!.showFullProgressbar()
        adapter!!.notifyAdapter(null, 0)

        ApiManager.getRecentMatch(PreferencesString.instance.getString("hirerId")!!)
                .subscribe ({
                    mView!!.hideFullProgressbar()
                    adapter!!.notifyAdapter(it, 0)
                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Erro", "Recent Match Contratei")
                })
    }

    private fun callWasHired(){
        mView!!.showFullProgressbar()
        adapter!!.notifyAdapter(null, 1)
        ApiManager.getRecentHirerMatch(PreferencesString.instance.getString("hirerId")!!)
                .subscribe ({
                    mView!!.hideFullProgressbar()
                    adapter!!.notifyAdapter(it.partidas, 1)
                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Erro", it.message)
                })
    }


    fun showAlert() {
        val alert = AlertaIos()

        alert.show(activity?.supportFragmentManager, "Deseja cancelar partidar?")
    }
}