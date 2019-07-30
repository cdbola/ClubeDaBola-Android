package cdbol.br.com.clubedabola.screens.cartao

import android.widget.EditText
import cdbol.br.com.clubedabola.model.HirerIdObj
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView

interface AdicionarCartaoContact {

    interface View : BaseMvpView {
        fun habilitaConcluir()
        fun desabilitaConcluir()
        fun showAlert()
        fun showErrorAlert()
        fun showLoading()
        fun hideLoading()

    }

    interface Presenter : BaseMvpPresenter<View> {
        fun setEditAddCard(hirer: HirerIdObj, edtNumeroCartao: EditText, edtVencimento: EditText, edtCvv: EditText, edt_full_name: EditText, edt_cpf: EditText,
                           edt_code: EditText, edt_ddd: EditText, edt_phone: EditText,
                           edt_street: EditText, edt_street_number: EditText, edt_complement: EditText, edt_neighbor: EditText,
                           edt_city: EditText, edt_cep: EditText, edt_state: EditText, edt_country: EditText)
        fun habilidaConcluir() : Boolean
        fun retornaResultado()
        fun postCreditCard()
    }
}