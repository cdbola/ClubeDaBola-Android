package cdbol.br.com.clubedabola.screens.contratacao

import android.location.Address
import android.util.Log
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.*
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.DateObjUtils
import cdbol.br.com.clubedabola.utils.DateUtils
import cdbol.br.com.clubedabola.view.*
import java.util.*


class NovaContratacaoPresenter(val hirer: HirerIdObj) : BaseMvpPresenterImpl<NovaContratacaoContract.View>(),
        NovaContratacaoContract.Presenter {


    private var itemMatch: ItemViewLabel? = null
    private var itemGender: ItemViewLabel? = null
    private var itemPlace: ItemViewLabel? = null
    private var itemDate: ItemViewLabel? = null
    private var itemStart: ItemViewLabel? = null
    //private var itemEnd: ItemViewLabel? = null
    private var itemDuration: ItemViewLabel? = null
    private var itemObs: ItemViewObservation? = null
    private var itemChangeType: ItemViewLabelAlterar? = null
    private var itemCard: ItemCartao? = null
    private var itemGoalkeeper: ItemGoleiro? = null


    private var datautils = DateUtils()

    private var itemMatchStr: String = ""
    private var itemGenderStr: String = ""
    private var itemAddress: Address? = null
    private var itemDateStr: String = ""
    private var itemStartStr: String = ""
    //private var itemEndStr: String = ""
    private var itemDurationStr: String = ""
    private var itemChangeTypeStr: String = ""
    private var itemObsStr: String = ""
    private var itemAmount: String = ""
    private var itemCvcStr: String = ""
    private var itemGoalkeeperId: String? = null


    var calEnd: Calendar? = null
    var calStart: Calendar? = null

    var duration: Int? = null

    override fun bindViews(itemMatch: ItemViewLabel?, itemGender: ItemViewLabel?, itemPlace: ItemViewLabel?,
                           itemDate: ItemViewLabel?, itemStart: ItemViewLabel?, itemDuration: ItemViewLabel?,
                           itemObs: ItemViewObservation?, itemChangeType: ItemViewLabelAlterar?, itemCard: ItemCartao, itemGoalkeeper: ItemGoleiro) {

        this.itemMatch = itemMatch
        this.itemGender = itemGender
        this.itemPlace = itemPlace
        this.itemDate = itemDate
        this.itemStart = itemStart
        //this.itemEnd = itemEnd
        this.itemDuration = itemDuration
        this.itemObs = itemObs
        this.itemChangeType = itemChangeType
        this.itemCard = itemCard
        this.itemGoalkeeper = itemGoalkeeper

    }

    private fun buildAmount(duration: Int) {

        if(duration == 60){
            itemAmount = "30"
        }
        else{
            if(duration == 90){
                itemAmount = "45"
            }
            else{
                itemAmount = "60"
            }
        }
        itemCard!!.build(itemAmount)

        checkBtn()
    }

//    override fun buildObs(obs: String) {
//        itemObs?.build(obs)
//        itemGoalkeeperId = choosed._id
//    }

    override fun buildGoalkeeper(choosed: ItemGoalkeeper) {
       itemGoalkeeper?.build(choosed)
        itemGoalkeeperId = choosed._id
    }

    override fun buildCvc(cvc: String) {
        itemCvcStr = cvc
        checkBtn()
    }

    override fun buildMatchType(item: String) {
        itemMatch?.build(item)
        itemMatchStr = item
        checkBtn()
    }

    override fun buildGender(item: String) {
        itemGender?.build(item)
        itemGenderStr = item
        checkBtn()
    }

    override fun buildAddress(item: Address?) {
        itemPlace?.build(item!!.getAddressLine(0))
        itemAddress = item
        checkBtn()
    }

    override fun buildDuration(item: String) {
        itemDuration?.build(item)
        itemDurationStr = item.substring(0,2)
        duration = itemDurationStr.toInt()
        buildAmount(duration!!)
        checkBtn()
    }

    /*override fun convertHourFinishText(hourOfDay: Int, minute: Int, text: String) {
        var strShow = datautils?.convertHourStartText(hourOfDay, minute, text)
        itemEnd?.build(strShow)
        itemEndStr = datautils?.hourApi(hourOfDay, minute)
        calEnd = DateObjUtils.toCalendar(hourOfDay, minute)
        duration = calEnd!!.get(Calendar.HOUR_OF_DAY) - calStart!!.get(Calendar.HOUR_OF_DAY)
        buildAmount(duration!!)
        checkBtn()
    }
*/

    override fun convertHourStartText(hourOfDay: Int, minute: Int, text: String) {
        var strShow = datautils?.convertHourStartText(hourOfDay, minute, text)
        itemStart?.build(strShow)
        itemStartStr = datautils?.hourApi(hourOfDay, minute)
        calStart = DateObjUtils.toCalendar(hourOfDay, minute)
        checkBtn()
    }


    override fun convertDateText(year: Int, month: Int, dayOfMonth: Int) {
        var dataFormatada = datautils?.convertDateNumeric(year, month, dayOfMonth)
        itemDate?.build(dataFormatada)
        itemDateStr = dataFormatada
        checkBtn()
    }


    override fun tipoContratacao(descricao: String) {
        itemChangeType?.build(descricao)
        itemChangeTypeStr = descricao

        checkBtn()
    }

    override fun tituloTela(name: String) {

    }

    private fun checkBtn() {
        if (itemMatchStr.isNotEmpty() &&
                itemGenderStr.isNotEmpty() &&
                itemDateStr.isNotEmpty() &&
                itemStartStr.isNotEmpty() &&
                //itemEndStr.isNotEmpty() &&
                itemDurationStr.isNotEmpty() &&
                itemChangeTypeStr.isNotEmpty() &&
                hirer.customerId.isNotEmpty()) {

            mView?.enableBtn(true)
        } else {
            mView?.enableBtn(false)
        }

    }

    override fun createMatch() {

        mView!!.showFullProgressbar()

        var amount = """${itemAmount}00""".toInt()
        var typePreference = itemGoalkeeperId
        var random = typePreference == null
        var match = MatchRequst(
                hirer.customerId,
                hirer.id,
                typePreference,
                itemMatchStr,
                itemGenderStr,
                DateObjUtils.formatDateServer(itemDateStr),
                AddressRequest(itemAddress!!.getAddressLine(0), "", "", "",
                        LocRequest(arrayOf(itemAddress!!.longitude, itemAddress!!.latitude))),
                itemStartStr,
                //itemEndStr,
                itemDurationStr,
                "",
                itemChangeTypeStr,
                random,
                amount,
                itemCvcStr
        )

        ApiManager.createMatch(match)
                .subscribe({
                    mView!!.hideFullProgressbar()
                    mView?.close()

                }, {
                    mView!!.hideFullProgressbar()
                    Log.d("Error: ", it.message)
                })
    }

}