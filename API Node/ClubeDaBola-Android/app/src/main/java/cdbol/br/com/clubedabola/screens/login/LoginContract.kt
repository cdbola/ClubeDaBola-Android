package cdbol.br.com.clubedabola.screens.login

import android.widget.Button
import android.widget.EditText
import cdbol.br.com.clubedabola.model.HirerResponse
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView

interface LoginContract {


    interface View : BaseMvpView {

        fun tokenUsuario(hirerResponse: HirerResponse)
        fun hidenProgressBar()
        fun showAlert()

    }

    interface Presenter : BaseMvpPresenter<View> {

        fun initEditText(edtUserName: EditText, edtSenha: EditText, button: Button)
        fun signin()
        fun faceLogin(jsonObject: String, email: String)

    }
}