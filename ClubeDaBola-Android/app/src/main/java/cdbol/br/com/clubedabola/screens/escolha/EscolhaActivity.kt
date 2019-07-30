package cdbol.br.com.clubedabola.screens.escolha

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.ChooseDataClass
import cdbol.br.com.clubedabola.model.ItemGoalkeeper
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.view.ItemGoleiro
import kotlinx.android.synthetic.main.activity_escolha.*

class EscolhaActivity : BaseMvpActivity<EscolhaContact.View,
        EscolhaContact.Presenter>(), EscolhaContact.View, ChooseAdapter.ClickListener {


    var concluir: TextView? = null
    var adapter : ChooseAdapter? = null
    override var mPresenter: EscolhaContact.Presenter = EscolhaPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escolha)

        setAdapter()
        initToolBar()
        init()

        autocomplete_escolha.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length >= 3){
                    callGetGoalkeeper(s.toString())
                }else{
                    adapter!!.notifyAdapter(null)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edt_item_goleiro_escolha.setOnClickListener {
            var choosed = ItemGoalkeeper("", "Aleatório", "image")
            var intent = Intent()
            intent.putExtra("goalkeeper", choosed)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

    fun setAdapter(){

        rl_hired_type.layoutManager = LinearLayoutManager(this)
        adapter = ChooseAdapter(this)
        rl_hired_type.adapter = adapter
    }


    fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toollbar_cadastro)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val cancelar = toolBar.findViewById<TextView>(R.id.toolbar_cancelar)
        val titulo = toolBar.findViewById<TextView>(R.id.toolbar_title)
        titulo.text = "Escolher Goleiro"
        cancelar.text = "Cancelar"
        cancelar.setOnClickListener {
            finish()
        }

        concluir = toolBar.findViewById(R.id.toolbar_concluir)
        concluir?.visibility = View.GONE
    }

    fun init() {
        mPresenter.attachView(this)
        val itemViewLabelAlterar = findViewById<ItemGoleiro>(R.id.edt_item_goleiro_escolha)
        mPresenter.setEdtEscolha(itemViewLabelAlterar)

    }
    override fun onClickCancel(goalkeeper: ChooseDataClass) {
        var choosed = ItemGoalkeeper(goalkeeper._id, goalkeeper.nome, goalkeeper.avatar)
        var intent = Intent()
        intent.putExtra("goalkeeper", choosed)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun callGetGoalkeeper(nickName: String) {
        adapter!!.notifyAdapter(null)
        ApiManager.getGoalkeeperByNickname(nickName)
                .subscribe ({
                    adapter!!.notifyAdapter(it)
                }, { Log.e("Error get goleiro", it.message)})
    }

}



