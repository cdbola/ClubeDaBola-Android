package cdbol.br.com.clubedabola.screens.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.Auth
import cdbol.br.com.clubedabola.model.FacebookLogin
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import com.facebook.login.LoginManager
import rx.functions.Action1

class LoginPresenter : BaseMvpPresenterImpl<LoginContract.View>(),
        LoginContract.Presenter {

    private var edtUserName: EditText? = null
    private var edtSenha: EditText? = null
    private var validPassword: Boolean = false
    private var validaEmail: Boolean = false


    override fun initEditText(edtUserName: EditText, edtSenha: EditText, button: Button) {

        this.edtUserName = edtUserName
        this.edtSenha = edtSenha

//        var colorRed = ColorStateList.valueOf(Color.RED)
//        var colorDefault = ColorStateList.valueOf(mView?.getContext()!!.resources.getColor(R.color.gray_828282))
//


        edtUserName.checkChange {
            if (it.isValidEmail() && validPassword) {
                button.isEnabled = it.isValidEmail()
            } else {
                button.isEnabled = false
            }
            validaEmail = it.isValidEmail()

        }

        edtSenha.checkChange {
            if (it.isValidPassword() && validaEmail) button.isEnabled = it.isValidPassword() else button.isEnabled = false
            validPassword = it.isValidPassword()
        }

    }

    override fun signin() {

        var auth = Auth(
                edtUserName?.text.toString(),
                edtSenha?.text.toString()
        )

        ApiManager.tokenRegister(auth)
                .subscribe(Action1 {

                    mView?.tokenUsuario(it)

                },
                        GeneralErrorHandler(mView, false) { _, _, _ -> mView?.showAlert() })


        mView?.hidenProgressBar()
    }




    override fun faceLogin(name: String, email: String) {

        var hirerSignUp = FacebookLogin(
                name,
                name, //nome completo
                "$name.png",
                "01/01/1990",
                email //email


        )

        mView!!.showFullProgressbar()


        ApiManager.postFaceLogin(hirerSignUp)
                .subscribe({
                    mView!!.hideFullProgressbar()
                    mView!!.tokenUsuario(it)

                }, {
                    LoginManager.getInstance().logOut()
                    mView!!.hideFullProgressbar()
                    mView!!.showAlert()
                    Log.d("Error create user", it.message)
                })


    }


    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged.invoke(s.toString())
            }
        })
    }

    private fun EditText.validate(validator: (String) -> Unit) {
        this.afterTextChanged {
            (validator(it))
        }
        (validator(this.text.toString()))
    }

    private fun EditText.checkChange(validator: (String) -> Unit) {
        this.onTextChanged {
            (validator(it))
        }
        (validator(this.text.toString()))

    }

    private fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isValidPassword(): Boolean = this.isNotEmpty() && this.length > 3


}