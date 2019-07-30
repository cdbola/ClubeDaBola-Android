package cdbol.br.com.clubedabola.screens.localmanual

import android.widget.EditText
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView

object LocalManualContract {

    interface View : BaseMvpView {
        fun habilitaConcluir()
        fun desabilitaConcluir()
        fun returnManualAddress(address: String)
    }

    interface Presenter : BaseMvpPresenter<View> {

        fun setEditTextEndereco(edt_endereco: EditText)
        fun setEditTextNumero(edt_numero: EditText)
        fun setEditTextComplemento(edt_complemento: EditText)
        fun setEditTextBairro(edt_bairro: EditText)
        fun habilidaConcluir() : Boolean
        fun retornaResultado()
        fun callAddress()

    }

}