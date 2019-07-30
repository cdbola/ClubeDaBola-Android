package cdbol.br.com.clubedabola.screens.cartao

import Mask
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.*
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.DateObjUtils
import cdbol.br.com.clubedabola.utils.FourDigitCardFormatWatcher
import cdbol.br.com.clubedabola.utils.PreferencesString
import rx.functions.Action1

class AdicionarCartaoPresenter : BaseMvpPresenterImpl<AdicionarCartaoContact.View>(),
        AdicionarCartaoContact.Presenter {


    private var activity: AdicionarCartaoContact.View? = null

    private var hirer: HirerIdObj? = null
    private var edtNumeroCartao: EditText? = null
    private var edtVencimento: EditText? = null
    private var edtCvv: EditText? = null
    private var edName: EditText? = null
    private var edCpf: EditText? = null
    private var edCode: EditText? = null
    private var edPhone: EditText? = null
    private var edDdd: EditText? = null
    private var edtStreet: EditText? = null
    private var edtStreetNumber: EditText? = null
    private var edtComplement: EditText? = null
    private var edtNeighbor: EditText? = null
    private var edtCity: EditText? = null
    private var edtCep: EditText? = null
    private var edtState: EditText? = null
    private var edtcountry: EditText? = null

    override fun setEditAddCard(hirer: HirerIdObj,edtNumeroCartao: EditText, edtVencimento: EditText, edtCvv: EditText,
                                edt_full_name: EditText, edt_cpf: EditText, edt_code: EditText, edt_ddd: EditText, edt_phone: EditText,
                                edt_street: EditText, edt_street_number: EditText, edt_complement: EditText, edt_neighbor: EditText,
                                edt_city: EditText, edt_cep: EditText, edt_state: EditText, edt_country: EditText) {


        this.hirer = hirer
        this.edName = edt_full_name
        this.edtNumeroCartao = edtNumeroCartao
        this.edtVencimento = edtVencimento
        this.edtCvv = edtCvv
        this.edCpf = edt_cpf
        this.edCode = edt_code
        this.edDdd = edt_ddd
        this.edPhone = edt_phone

        this.edtStreet = edt_street
        this.edtStreetNumber = edt_street_number
        this.edtComplement = edt_complement
        this.edtNeighbor = edt_neighbor
        this.edtCity = edt_city
        this.edtCep = edt_cep
        this.edtState = edt_state
        this.edtcountry = edt_country


        var fourDigitCardFormatWatcher = FourDigitCardFormatWatcher()
        this.edtNumeroCartao?.addTextChangedListener(fourDigitCardFormatWatcher)

        this.edName!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
        this.edtVencimento?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edtCvv?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        this.edCpf!!.addTextChangedListener(Mask.mask("###.###.###-##", edt_cpf))

        this.edCpf!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edCode!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edDdd!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                retornaResultado()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        this.edPhone!!.addTextChangedListener(object : TextWatcher {
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
        return !(edtNumeroCartao?.text.toString().length < 14
                && edtVencimento?.text.toString().length < 5
                && edtCvv?.text.toString().length < 3)
                && edName!!.text.isNotEmpty()
                && edCpf!!.text.isNotEmpty()
                && edCode!!.text.isNotEmpty()
                && edDdd!!.text.isNotEmpty()
                && edPhone!!.text.isNotEmpty()
    }

    override fun retornaResultado() {

        if (habilidaConcluir()) {
            activity?.habilitaConcluir()
        } else {
            activity?.desabilitaConcluir()
        }
    }

    override fun postCreditCard() {
        val expiration = edtVencimento!!.text.toString().split("/").toTypedArray()
        val cardNumber = edtNumeroCartao!!.text.toString().replace("\\s".toRegex(), "")
        val cpf = edCpf!!.text.toString().replace("""[$.-]""".toRegex(), "")
        val birthdayApi = DateObjUtils.formatDateApi(hirer!!.birthday)
        val cvv = edtCvv!!.text.toString()
        val name = edName!!.text.toString()
        val code = edCode!!.text.toString()
        val ddd = edDdd!!.text.toString()
        val phone = edPhone!!.text.toString()
        val city = edtCity!!.text.toString()
        val neighbor = edtNeighbor!!.text.toString()
        val street = edtStreet!!.text.toString()
        val streetNumber = edtStreetNumber!!.text.toString()
        val cep = edtCep!!.text.toString()
        val state = edtState!!.text.toString()
        val country = edtcountry!!.text.toString()
        val complement = edtComplement!!.text.toString()

        var request = CreditCardRequest(
                CreditCard(cvv, expiration[0], expiration[1],
                        Holder(birthdayApi, name,
                                Phone(code, ddd, phone),
                                TaxDocument(cpf, "CPF"), null),
                        cardNumber),
                hirer!!.customerId
        )

        var customerRequest = CustomerRequest(hirer!!.id, name, hirer!!.email, birthdayApi,
                TaxDocument(cpf, "CPF"),
                Phone(code, ddd, phone),
                FundingInstrument(
                        CreditCard(cvv, expiration[0], expiration[1],
                                Holder(birthdayApi, name,
                                    Phone(code, ddd, phone),
                                    TaxDocument(cpf, "CPF"),
                                    BillingAddress(city, neighbor, street, streetNumber, cep, state, country)),
                                cardNumber
                        ), "CREDIT_CARD"),
                ShippingAddress(city, complement, neighbor, street, streetNumber, cep, state, country)
        )


        mView!!.showLoading()

        if (hirer!!.customerId.isNullOrEmpty()){
            ApiManager.postCustomer(customerRequest)
                    .subscribe ({
                        getHirerId()
                    }, {
                        mView!!.showErrorAlert()
                        Log.d("Error", it.message)
                    }
                    )
        }else{
            ApiManager.postCreditCard(request)
                    .subscribe ({
                        getHirerId()
                    }, {
                        mView!!.showErrorAlert()
                        Log.d("Error", it.message)
                    }
                    )
        }

    }

    private fun getHirerId() {
        ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                .subscribe ({
                    mView!!.showAlert()
                }, {
                    mView!!.showErrorAlert()
                    Log.d("Error", it.message)
                }
                )

    }

    override fun attachView(view: AdicionarCartaoContact.View) {
        super.attachView(view)
        activity = view
    }


}