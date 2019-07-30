package cdbol.br.com.clubedabola.screens.cadastro

import android.content.Context
import cdbol.br.com.clubedabola.model.HirerResponse
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView
import cdbol.br.com.clubedabola.view.CustomViewEditText

interface CadastroContract {
    interface View : BaseMvpView {
        fun habilitaConcluir()
        fun desabilitaConcluir()
        fun register()
        fun tokenUsuario(hirerResponse: HirerResponse)
        fun hideProgressBar()
        fun showSimpleAlert(context: Context, message: String)

    }

    interface Presenter : BaseMvpPresenter<CadastroContract.View> {


        fun initEditText(edtUserName: CustomViewEditText,
                         edtUserApelido: CustomViewEditText,
                         edtUserDataNascimento: CustomViewEditText,
                         edtUserEmail: CustomViewEditText,
                         edtUserSenha: CustomViewEditText)

        fun habilidaConcluir(): Boolean
        fun retornaResultado()
        fun registraCliente()
        fun token()
        fun convertDateText(year: Int, month: Int, dayOfMonth: Int)
    }
}