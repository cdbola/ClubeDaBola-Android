package cdbol.br.com.clubedabola.screens.customer

import android.widget.EditText
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView

interface CustomerContact {

    interface View : BaseMvpView {
        fun habilitaConcluir()
        fun desabilitaConcluir()

    }

    interface Presenter : BaseMvpPresenter<View> {
        fun setEditAddCard( edtNumeroCartao : EditText , edtVencimento : EditText , edtCvv : EditText)
        fun habilidaConcluir() : Boolean
        fun retornaResultado()
    }
}