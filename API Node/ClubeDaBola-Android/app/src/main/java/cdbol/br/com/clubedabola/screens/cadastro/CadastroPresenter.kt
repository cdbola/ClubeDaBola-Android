package cdbol.br.com.clubedabola.screens.cadastro

import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.Auth
import cdbol.br.com.clubedabola.model.Hirer
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.DateUtils
import cdbol.br.com.clubedabola.view.CustomViewEditText
import rx.functions.Action1

class CadastroPresenter : BaseMvpPresenterImpl<CadastroContract.View>(),
        CadastroContract.Presenter {
    override fun convertDateText(year: Int, month: Int, dayOfMonth: Int) {

        var dataFormatada = datautils?.convertDateNumeric(year, month, dayOfMonth)
        edtUserDataNascimento?.customEditText?.setText(dataFormatada)

    }

    override fun token() {

        var auth = Auth(
                edtUserEmail?.text.toString(),
                edtUserSenha?.text.toString()
        )

        ApiManager.tokenRegister(auth)
                .doOnError { activity?.showMessage(it.toString()) }
                .subscribe(Action1 { activity?.tokenUsuario(it) },
                        GeneralErrorHandler(mView, true) { throwable, errorBody, isNetwork -> mView?.showError(throwable.message) })

    }

    override fun registraCliente() {

        var hirerSignUp = Hirer(
                edtUserName?.text.toString(),
                edtUserApelido?.text.toString(),
                edtUserName?.text.toString()+".png",
                edtUserDataNascimento?.text.toString(),
                edtUserEmail?.text.toString(),
                edtUserSenha?.text.toString()
        )


        ApiManager.createHirer(hirerSignUp)
                .subscribe ({
                    Log.d("Success register", "")
                    activity?.register()
                }, {
                    mView!!.hideProgressBar()
                    mView!!.showSimpleAlert(mView!!.getContext(), mView!!.getContext().getString(R.string.text_already_created))
                    Log.d("Error resgate", it.message)
                })

    }

    override fun habilidaConcluir(): Boolean {
        return !(edtUserName?.text.toString().length < 3
                || edtUserApelido?.text.toString().length < 2
                || edtUserDataNascimento?.text.toString().length < 3
                || edtUserEmail?.text.toString().length < 3
                || edtUserSenha?.text.toString().length < 6)

    }

    override fun retornaResultado() {
        if (habilidaConcluir()) {
            activity?.habilitaConcluir()
        } else {
            activity?.desabilitaConcluir()
        }
    }

    private fun registraEditText(editText: CustomViewEditText) {

        var customEditText = editText.findViewById<TextInputEditText>(R.id.custom_edit_text)
        customEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    var edtUserName: CustomViewEditText? = null
    var edtUserApelido: CustomViewEditText? = null
    var edtUserDataNascimento: CustomViewEditText? = null
    var edtUserEmail: CustomViewEditText? = null
    var edtUserSenha: CustomViewEditText? = null
    var activity: CadastroContract.View? = null
    var datautils = DateUtils()


    override fun attachView(view: CadastroContract.View) {
        super.attachView(view)
        activity = view
    }

    override fun initEditText(edtUserName: CustomViewEditText, edtUserApelido: CustomViewEditText, edtUserDataNascimento: CustomViewEditText, edtUserEmail: CustomViewEditText, edtUserSenha: CustomViewEditText) {

        this.edtUserName = edtUserName
        this.edtUserApelido = edtUserApelido
        this.edtUserDataNascimento = edtUserDataNascimento
        this.edtUserEmail = edtUserEmail
        this.edtUserSenha = edtUserSenha

        this.edtUserDataNascimento?.customEditText!!.addTextChangedListener(Mask.mask("##/##/####", this.edtUserDataNascimento!!.customEditText))
        registraEditText(this.edtUserName!!)
        registraEditText(this.edtUserApelido!!)
        registraEditText(this.edtUserDataNascimento!!)
        registraEditText(this.edtUserEmail!!)
        registraEditText(this.edtUserSenha!!)
    }



}