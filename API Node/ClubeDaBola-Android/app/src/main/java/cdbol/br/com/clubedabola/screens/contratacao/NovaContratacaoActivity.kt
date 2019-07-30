package cdbol.br.com.clubedabola.screens.contratacao

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.ItemGoalkeeper
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.cartao.AdicionarCartaoActivity
import cdbol.br.com.clubedabola.screens.escolha.EscolhaActivity
import cdbol.br.com.clubedabola.screens.local.LocalActivity
import cdbol.br.com.clubedabola.view.*
import kotlinx.android.synthetic.main.activity_nova_contratacao.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NovaContratacaoActivity : BaseMvpActivity<
        NovaContratacaoContract.View,
        NovaContratacaoContract.Presenter>(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        NovaContratacaoContract.View {


    var spinnerDialog: SpinnerDialog? = null
    var spinnerDialogGenero: SpinnerDialog? = null
    var spinnerDialogContratacao: SpinnerDialog? = null
    var spinnerDialogDuracao: SpinnerDialog? = null

    var REQUEST_LOCAL = 1000

    var CHOOSED_PLAYER = 8000

    var ADD_CREDIT_CARD = 9888


    val listaCampo = arrayListOf<String>()
    val listaGenero = arrayListOf<String>()
    val tipoContratacao = arrayListOf<String>()
    val listaDuracao = arrayListOf<String>()

    var text: String = ""

    val RESULT_AUTO = -1111

    var cal: Calendar? = null

    override var mPresenter: NovaContratacaoContract.Presenter = NovaContratacaoPresenter(getHirer())

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_contratacao)

        configuraToolBar("Nova Contatação")
        mPresenter.attachView(this)
        initView()
        var tipo = intent.getStringExtra("tipo")
        mPresenter.tipoContratacao(tipo)

        edt_tipo_jogo.setOnClickListener {
            spinnerDialog!!.showSpinerDialog()
        }

        edt_genero.setOnClickListener {
            spinnerDialogGenero!!.showSpinerDialog()
        }

        var alterar = edt_tipo_solicitacao.findViewById<TextView>(R.id.txtAlterar)

        alterar.setOnClickListener {
            spinnerDialogContratacao!!.showSpinerDialog()
        }

        edt_local.setOnClickListener {
            val intent = Intent(this, LocalActivity::class.java)
            startActivityForResult(intent, REQUEST_LOCAL)
        }

        cal = Calendar.getInstance()


        edt_data.setOnClickListener {

            DatePickerDialog(this@NovaContratacaoActivity, this,
                    cal!!.get(Calendar.YEAR),
                    cal!!.get(Calendar.MONTH),
                    cal!!.get(Calendar.DAY_OF_MONTH)).show()

        }


        edt_hora_inicial.setOnClickListener {
            TimePickerDialog(this@NovaContratacaoActivity, AlertDialog.THEME_HOLO_LIGHT,this, cal!!.get(Calendar.HOUR),
                    cal!!.get(Calendar.MINUTE), true).show()
            text = "Das "
        }

        edt_duracao.setOnClickListener {
            spinnerDialogDuracao!!.showSpinerDialog()
        }

/*        edt_hora_final.setOnClickListener {
         TimePickerDialog(this@NovaContratacaoActivity, AlertDialog.THEME_HOLO_LIGHT,this, cal!!.get(Calendar.HOUR),
                    cal!!.get(Calendar.MINUTE), true).show()
            text = "às "


        }
*/
        edt_item_goleiro.setOnClickListener {
            val intent = Intent(this, EscolhaActivity::class.java)
            startActivityForResult(intent,CHOOSED_PLAYER)
        }

        item_card.setOnClickListener {
            val intent = Intent(this,AdicionarCartaoActivity::class.java)

            startActivityForResult(intent, ADD_CREDIT_CARD)
        }

        btn_hirer.setOnClickListener {
            alertCvc()
        }

    }


    override fun enableBtn(b: Boolean) {
        btn_hirer.isEnabled = b
    }

    override fun close(){
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_LOCAL){
            if(resultCode == Activity.RESULT_OK){

                var address: Address? = null
                address = data?.getParcelableExtra("address")
                mPresenter.buildAddress(address)

            }else if(resultCode == RESULT_AUTO){
                if(resultCode == Activity.RESULT_OK){

                }

            }
        }else if(requestCode == CHOOSED_PLAYER){
            if(resultCode == Activity.RESULT_OK){

                var goalkeeper = data?.extras!!.get("goalkeeper") as ItemGoalkeeper

                mPresenter.buildGoalkeeper(goalkeeper)
            }

        }else if(requestCode == ADD_CREDIT_CARD) {
            if (resultCode == Activity.RESULT_OK) {

                var cvv = data?.extras!!.get("cvv") as String
                mPresenter.buildCvc(cvv)

            }
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        mPresenter.convertDateText(year, month, dayOfMonth)
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        //if (text.contentEquals("Das ")) {
            mPresenter.convertHourStartText(hourOfDay, minute, text)

        //} else {
         //   mPresenter.convertHourFinishText(hourOfDay, minute, text)
        //}
        text = ""
    }


    fun initView() {
        val itemChangeType = findViewById<ItemViewLabelAlterar>(R.id.edt_tipo_solicitacao)

        val itemMatch = findViewById<ItemViewLabel>(R.id.edt_tipo_jogo)
        val itemGender = findViewById<ItemViewLabel>(R.id.edt_genero)
        val itemPlace = findViewById<ItemViewLabel>(R.id.edt_local)
        val itemData = findViewById<ItemViewLabel>(R.id.edt_data)
        val itemStart = findViewById<ItemViewLabel>(R.id.edt_hora_inicial)
        val itemDuration = findViewById<ItemViewLabel>(R.id.edt_duracao)
        //val itemEnd = findViewById<ItemViewLabel>(R.id.edt_hora_final)
        val itemObs = findViewById<ItemViewObservation>(R.id.edt_observacao)
        val itemCard = findViewById<ItemCartao>(R.id.item_card)
        val itemGoalkeeper =  findViewById<ItemGoleiro>(R.id.edt_item_goleiro)


        mPresenter.bindViews(itemMatch, itemGender, itemPlace, itemData, itemStart, itemDuration, itemObs, itemChangeType, itemCard, itemGoalkeeper)

        iniciaLista()
        iniciaCombos()
    }

    fun iniciaLista() {
        listaCampo.add("Society")
        listaCampo.add("Futsal")
        listaCampo.add("Campo Grama Sintética")
        listaCampo.add("Campo Terrão")
        listaCampo.add("Campo Grama Natural")

        listaGenero.add("Masculino")
        listaGenero.add("Feminino")

        tipoContratacao.add("Goleiro")
        tipoContratacao.add("Árbitro")

        listaDuracao.add("60 minutos")
        listaDuracao.add("90 minutos")
        listaDuracao.add("120 minutos")
    }

    fun iniciaCombos() {
        spinnerDialog = SpinnerDialog(this, listaCampo, "Selecione o Tipo de Jogo", "Fechar")
        spinnerDialogGenero = SpinnerDialog(this, listaGenero, "Selecione o Gênero","Fechar")

        spinnerDialogContratacao = SpinnerDialog(this, tipoContratacao, "Selecione ", "Fechar")
        spinnerDialogDuracao = SpinnerDialog(this,listaDuracao, "Selecione a Duração", "Fechar")
        initShowCombos()
    }

    fun initShowCombos() {
        spinnerDialog!!.bindOnSpinerListener { item, _ ->
            mPresenter.buildMatchType(item)

        }

        spinnerDialogGenero!!.bindOnSpinerListener { item, _ ->
            mPresenter.buildGender(item)
        }

        spinnerDialogContratacao!!.bindOnSpinerListener { item, position ->
            if (item.toString().contentEquals("Goleiro")) {
                mPresenter.tipoContratacao("G")

            }

            if (item.toString().contentEquals("Árbitro")) {
                mPresenter.tipoContratacao("A")
            }
        }

        spinnerDialogDuracao!!.bindOnSpinerListener { item, _ ->
            mPresenter.buildDuration(item)
        }
    }

    fun alertCvc() {
        val builder = android.support.v7.app.AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Digite seu cvc:")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_cvc, null)
        val cvc  = dialogLayout.findViewById<EditText>(R.id.edt_cvc)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") {
            _, _ ->
            run {
                mPresenter.buildCvc(cvc.text.toString())
                mPresenter.createMatch()
            }
        }
        builder.show()
    }

}