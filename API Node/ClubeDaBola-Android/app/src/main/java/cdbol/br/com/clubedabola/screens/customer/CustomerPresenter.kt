package cdbol.br.com.clubedabola.screens.customer

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.FourDigitCardFormatWatcher

class CustomerPresenter : BaseMvpPresenterImpl<CustomerContact.View>(),
        CustomerContact.Presenter {

    override fun habilidaConcluir(): Boolean {
        return !(edtNumeroCartao?.text.toString().length < 14
                || edtVencimento?.text.toString().length < 5
                || edtCvv?.text.toString().length < 3)
    }

    override fun retornaResultado() {

        if (habilidaConcluir()) {
            activity?.habilitaConcluir()
        } else {
            activity?.desabilitaConcluir()
        }
    }

    override fun setEditAddCard(edtNumeroCartao: EditText, edtVencimento: EditText, edtCvv: EditText) {

        this.edtNumeroCartao = edtNumeroCartao
        this.edtVencimento = edtVencimento
        this.edtCvv = edtCvv
        var fourDigitCardFormatWatcher = FourDigitCardFormatWatcher()
        this.edtNumeroCartao?.addTextChangedListener(fourDigitCardFormatWatcher)
        this.edtNumeroCartao?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edtVencimento?.addTextChangedListener(Mask.mask("##/##", this.edtVencimento!!))
        this.edtVencimento?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edtCvv?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    var activity: CustomerContact.View? = null
    var edtNumeroCartao: EditText? = null
    var edtVencimento: EditText? = null
    var edtCvv: EditText? = null

    override fun attachView(view: CustomerContact.View) {
        super.attachView(view)
        activity = view
    }


}