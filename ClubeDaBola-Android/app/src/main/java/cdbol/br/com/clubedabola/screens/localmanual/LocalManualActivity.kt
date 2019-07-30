package cdbol.br.com.clubedabola.screens.localmanual

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity

class LocalManualActivity : BaseMvpActivity<LocalManualContract.View,
        LocalManualContract.Presenter>(), LocalManualContract.View {

    @UiThread
    override fun desabilitaConcluir() {

        concluir?.alpha = 0.5f

    }

    @UiThread
    override fun habilitaConcluir() {
        concluir?.alpha = 1f
    }

    override var mPresenter: LocalManualContract.Presenter = LocalManualPresenter()
    var concluir: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_manual)

        mPresenter.attachView(this)
        initToolBar()
        init()

    }

    fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toollbar_cadastro)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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
            if (mPresenter.habilidaConcluir()) {
                mPresenter.callAddress()

            }
        }
    }

    fun init() {

        val edtEndereco = findViewById<EditText>(R.id.edt_endereco)
        val edtNumero = findViewById<EditText>(R.id.edt_numero)
        val edtComplemento = findViewById<EditText>(R.id.edt_complemento)
        val edtBairro = findViewById<EditText>(R.id.edt_bairro)

        mPresenter.setEditTextEndereco(edtEndereco)
        mPresenter.setEditTextNumero(edtNumero)
        mPresenter.setEditTextComplemento(edtComplemento)
        mPresenter.setEditTextBairro(edtBairro)
    }

    override fun returnManualAddress(address: String) {

        var intent = Intent()
        intent.putExtra("address", address)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

}

