package cdbol.br.com.clubedabola.screens.localmanual

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import cdbol.br.com.clubedabola.fromJson
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import client.yalantis.com.githubclient.api.ErrorBody
import com.squareup.moshi.Moshi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class LocalManualPresenter : BaseMvpPresenterImpl<LocalManualContract.View>(),
        LocalManualContract.Presenter {

    var edtEndereco: EditText? = null
    var edtNumero: EditText? = null
    var edtComplemento: EditText? = null
    var edtBairo: EditText? = null
    var activity: LocalManualContract.View? = null

    override fun attachView(view: LocalManualContract.View) {
        super.attachView(view)
        activity = view
    }

    override fun setEditTextEndereco(edt_endereco: EditText) {

        edtEndereco = edt_endereco

        edtEndereco?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    override fun setEditTextNumero(edt_numero: EditText) {

        edtNumero = edt_numero

        edtNumero?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun setEditTextComplemento(edt_complemento: EditText) {
        edtComplemento = edt_complemento

    }

    override fun setEditTextBairro(edt_bairro: EditText) {
        edtBairo = edt_bairro
        edtBairo?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun habilidaConcluir(): Boolean {

        return !(edtEndereco?.text.toString().length < 3
                || edtBairo?.text.toString().length < 2
                || edtNumero?.text.toString().length < 2)
    }

    override fun retornaResultado() {

        if (habilidaConcluir()) {
            activity?.habilitaConcluir()
        } else {
            activity?.desabilitaConcluir()
        }


    }

    override fun callAddress() {
        val executor = Executors.newScheduledThreadPool(5)

        doAsync(executorService = executor) {
            val result = URL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyAl-Bc4ABRvsR3J-KQLaInNN2beuuDBWEM&address=" + edtEndereco?.text.toString() + edtNumero?.text.toString()
                    + edtBairo?.text.toString()).readText()
            uiThread {
                mView!!.returnManualAddress(result)
            }
        }

    }


}