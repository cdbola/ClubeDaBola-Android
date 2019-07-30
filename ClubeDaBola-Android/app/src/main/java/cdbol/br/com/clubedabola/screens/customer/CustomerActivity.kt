package cdbol.br.com.clubedabola.screens.customer

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity

class CustomerActivity : BaseMvpActivity<CustomerContact.View,
        CustomerContact.Presenter>(), CustomerContact.View {

    @UiThread
    override fun habilitaConcluir() {
        concluir?.alpha = 1f

    }

    @UiThread
    override fun desabilitaConcluir() {
        concluir?.alpha = 0.5f
    }

    override var mPresenter: CustomerContact.Presenter = CustomerPresenter()
    var concluir: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
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
            //if (mPresenter.habilidaConcluir()) {

            //}
        }
    }

    fun init() {
        mPresenter.attachView(this)

        var edtNumeroCartao = findViewById<EditText>(R.id.edt_numero_cartao)
        var edtVencimento = findViewById<EditText>(R.id.edt_vencimento)
        var edtCvv = findViewById<EditText>(R.id.edt_cvv)
        mPresenter.setEditAddCard(edtNumeroCartao,edtVencimento,edtCvv)

    }

}
