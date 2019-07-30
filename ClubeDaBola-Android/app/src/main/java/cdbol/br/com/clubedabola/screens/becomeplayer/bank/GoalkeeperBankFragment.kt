package cdbol.br.com.clubedabola.screens.becomeplayer.bank


import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.Bank
import cdbol.br.com.clubedabola.screens.becomeplayer.BecomePlayerContract
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_goalkeeper_bank.*


class GoalkeeperBankFragment : Fragment(), BecomePlayerContract.PageView{


    private var bankList = arrayListOf<String>()
    private var banks: Array<Bank>? = null
    private var bankPicker: SpinnerDialog? = null

    private var mainView: BecomePlayerContract.View? = null

    private var bankData : BankData? = null

    var bankName: String = ""
    var agency: String = ""
    var account: String = ""
    var cpf: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goalkeeper_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = activity as BecomePlayerContract.View
        et_select_cpf.addTextChangedListener(Mask.mask("###.###.###-##", et_select_cpf))

        et_select_agency.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                agency = s.toString()
                enableNextPosition()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        et_select_account.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                account = s.toString()
                enableNextPosition()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        et_select_cpf.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cpf = s.toString()
                enableNextPosition()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            initBankPicker()
            enableNextPosition()
        }
    }

    private fun initBankPicker() {

        Thread {
            val jsonfile: String = context!!.assets.open("banklist.json").bufferedReader().use { it.readText() }
            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            val jsonAdapter = moshi.adapter<Array<Bank>>(Array<Bank>::class.java)

            banks = jsonAdapter.fromJson(jsonfile)

            for (bank: Bank in banks!!) {
                bankList.add(bank.Code + " " + bank.Name)
            }

        }.run()


        bankPicker = SpinnerDialog(activity, bankList, activity!!.getString(R.string.title_select_bank), R.style.DialogAnimations_SmileWindow, "Fechar")

        container_bank.setOnClickListener({
            bankPicker!!.showSpinerDialog()
        })

        bankPicker!!.bindOnSpinerListener { item, _ ->
            tv_select.text = item
            bankName = item

        }
    }

    private fun buildBankData() : BankData{
        return BankData(bankName, agency, account, cpf)
    }


    override fun enableNextPosition() {

        if (et_select_agency.text.isNotEmpty() && et_select_account.text.isNotEmpty() && et_select_cpf.text.isNotEmpty() && bankName.isNotEmpty()) {
            mainView!!.onNext(true)
            mainView!!.mainPresenter().saveBankData(buildBankData())
        } else {
            mainView!!.onNext(false)
        }

    }

}
