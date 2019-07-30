package cdbol.br.com.clubedabola.screens.cadastro

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.HirerResponse
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.home.HomeActivity
import cdbol.br.com.clubedabola.screens.login.LoginActivity
import cdbol.br.com.clubedabola.screens.webview.WebGenericActivity
import cdbol.br.com.clubedabola.utils.PreferencesString
import cdbol.br.com.clubedabola.view.CustomViewEditText
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.intentFor
import java.util.*

class CadastroActivity : BaseMvpActivity<CadastroContract.View,
        CadastroContract.Presenter>(), CadastroContract.View, DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mPresenter.convertDateText(year, month, dayOfMonth)
    }

    override fun tokenUsuario(hirerResponse: HirerResponse) {

        progressBar.visibility = View.GONE
        PreferencesString.instance.put("token", hirerResponse.token)
        PreferencesString.instance.put("hirerId", hirerResponse.contratante._id)
        PreferencesString.instance.put("nickName", hirerResponse.contratante.apelido)

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun register() {
        mPresenter.token()
    }

    @UiThread
    override fun habilitaConcluir() {
        concluir?.alpha = 1f
    }

    @UiThread
    override fun desabilitaConcluir() {
        concluir?.alpha = 0.5f
    }

    var concluir: TextView? = null

    override var mPresenter: CadastroContract.Presenter = CadastroPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        mPresenter.attachView(this)
        var cal = Calendar.getInstance()

//       edt_user_data_nascimento.setOnClickListener {
//           DatePickerDialog(this@CadastroActivity, this,
//                   cal.get(Calendar.YEAR),
//                   cal.get(Calendar.MONTH),
//                   cal.get(Calendar.DAY_OF_MONTH)).show()
//
//       }


        initToolBar()
        init()
    }

    fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toollbar_cadastro)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val cancelar = toolBar.findViewById<TextView>(R.id.toolbar_cancelar)
        val titulo = toolBar.findViewById<TextView>(R.id.toolbar_title)
        // titulo.text = "Escolher Goleiro"
        cancelar.text = "Cancelar"
        cancelar.setOnClickListener {
            onBackPressed()
        }

        concluir = toolBar.findViewById(R.id.toolbar_concluir)
        desabilitaConcluir()

        concluir?.setOnClickListener {
            if (mPresenter.habilidaConcluir()) {
                progressBar.visibility = View.VISIBLE
                mPresenter.registraCliente()
            }
        }
    }

    override fun showError(error: String?) {
        super.showError(error)
        progressBar.visibility = View.GONE
    }

    override fun showError(stringResId: Int) {
        super.showError(stringResId)
        progressBar.visibility = View.GONE

    }


    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun init() {
        val edtUserName = findViewById<CustomViewEditText>(R.id.edt_user_name)
        val edtUserApelido = findViewById<CustomViewEditText>(R.id.edt_user_apelido)
        val edtUserDataNascimento = findViewById<CustomViewEditText>(R.id.edt_user_data_nascimento)
        val edtUserEmail = findViewById<CustomViewEditText>(R.id.edt_user_mail)
        val edtUserSenha = findViewById<CustomViewEditText>(R.id.edt_user_senha)
        mPresenter.initEditText(edtUserName, edtUserApelido, edtUserDataNascimento, edtUserEmail, edtUserSenha)

        terms.setOnClickListener { openTermsWed() }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun openTermsWed() {
        startActivity(intentFor<WebGenericActivity>("url" to "https://cdbola.com.br/termos.pdf"))
    }

}
