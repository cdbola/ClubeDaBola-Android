package cdbol.br.com.clubedabola.screens.cartao

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_adicionar_cartao.*
import kotlinx.android.synthetic.main.include_customer_address.*
import kotlinx.android.synthetic.main.include_customer_phone.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class AdicionarCartaoActivity : BaseMvpActivity<AdicionarCartaoContact.View,
        AdicionarCartaoContact.Presenter>(), AdicionarCartaoContact.View {

    @UiThread
    override fun habilitaConcluir() {
        concluir?.alpha = 1f

    }

    @UiThread
    override fun desabilitaConcluir() {
        concluir?.alpha = 0.5f
    }

    override var mPresenter: AdicionarCartaoContact.Presenter = AdicionarCartaoPresenter()
    var concluir: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_cartao)
        initToolBar()
        init()
    }

    fun initToolBar() {

        val toolBar = findViewById(R.id.toollbar_cadastro) as Toolbar
        setSupportActionBar(toolBar!!)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        val cancelar = toolBar.findViewById<TextView>(R.id.toolbar_cancelar)
        val titulo = toolBar.findViewById<TextView>(R.id.toolbar_title)
        titulo.text = "Local"
        cancelar.text = "Cancelar"
        cancelar.setOnClickListener {
            finish()
        }

        concluir = toolBar.findViewById(R.id.toolbar_concluir)
        desabilitaConcluir()

        concluir?.setOnClickListener {
          mPresenter.postCreditCard()
        }
    }

    fun init() {
        mPresenter.attachView(this)
        var edtNumeroCartao = findViewById<EditText>(R.id.edt_numero_cartao)
        var edtVencimento = findViewById<EditText>(R.id.edt_vencimento)
        var edtCvv = findViewById<EditText>(R.id.edt_cvv)

        if (getHirer().customerId.isNullOrBlank()){
            layout_address.visibility = View.VISIBLE
        }else{
            layout_address.visibility = View.GONE
        }
        mPresenter.setEditAddCard(getHirer(),edtNumeroCartao,edtVencimento,edtCvv,
                edt_full_name, edt_cpf, edt_code, edt_ddd, edt_phone,
                edt_street, edt_street_number, edt_complement,edt_neighbor, edt_city, edt_cep, edt_state, edt_country
        )

    }

    override fun showAlert(){
        hideLoading()
        alert("Cartão adicionado!") {
            title = "Sucesso!"
            yesButton {
                var intent = Intent()
                intent.putExtra("cvv", edt_cvv.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }.show()
    }

    override fun showErrorAlert(){
        hideLoading()
        alert("Tente novamente") {
            title = "Ops"
            yesButton {}
        }.show()
    }

    override fun showLoading(){
        showLoadingDialog("Adicionando...", "")
    }

    override fun hideLoading(){
        hideLoadingDialog()
    }



}
