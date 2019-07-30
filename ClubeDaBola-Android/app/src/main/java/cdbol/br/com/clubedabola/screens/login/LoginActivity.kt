package cdbol.br.com.clubedabola.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.HirerResponse
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.cadastro.CadastroActivity
import cdbol.br.com.clubedabola.screens.home.HomeActivity
import cdbol.br.com.clubedabola.screens.webview.WebGenericActivity
import cdbol.br.com.clubedabola.utils.PreferencesString
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.yesButton
import java.util.*


class LoginActivity : BaseMvpActivity<LoginContract.View,
        LoginContract.Presenter>(), LoginContract.View {


    private var callbackManager: CallbackManager? = null

    override fun hidenProgressBar() {
        progressBarLogin.visibility = View.GONE
    }

    override fun tokenUsuario(hirerResponse: HirerResponse) {

        PreferencesString.instance.put("token", hirerResponse.token)
        PreferencesString.instance.put("hirerId", hirerResponse.contratante._id)
        PreferencesString.instance.put("nickName", hirerResponse.contratante.apelido)
        startHome()

    }

    private fun startHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun showMessage(message: String) {
        super.showMessage(message)
        hidenProgressBar()
    }

    override fun showAlert() {
        alert("Tente novamente") {
            title = "Ops"
            yesButton {}
        }.show()
    }


    override var mPresenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()
        mPresenter.attachView(this)


        txt_novo_cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
            finish()
        }

        txt_ajuda.setOnClickListener {
            openHelp()
        }

        init()

        button.setOnClickListener {
            mPresenter.signin()
            progressBarLogin.visibility = View.VISIBLE

        }

        btn_face.setReadPermissions(Arrays.asList("public_profile"))
        btn_face.setReadPermissions("email")
        btn_face.setOnClickListener {
            facebookLogin()
        }

    }

    private fun facebookLogin() {

        btn_face.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val request = GraphRequest.newMeRequest(loginResult.accessToken)
                { jsonObject, response ->
                    mPresenter.faceLogin(jsonObject.getString("name"), jsonObject.getString("email"))
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,birthday")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
            }

            override fun onError(exception: FacebookException) {
                showSimpleAlert(this@LoginActivity, "Ops, algo deu errado.\nTente novamente")

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun init() {
        val edtUserEmail = findViewById<EditText>(R.id.edt_email_user)
        val edtUserSenha = findViewById<EditText>(R.id.edt_password_user)
        val button = findViewById<Button>(R.id.button)

        mPresenter.initEditText(edtUserEmail, edtUserSenha, button)
    }

    private fun openHelp() {
        startActivity(intentFor<WebGenericActivity>("url" to "https://cdbola.com.br"))
    }


}
