package cdbol.br.com.clubedabola.screens.contratacao

import android.content.Context
import android.location.Address
import cdbol.br.com.clubedabola.model.ChooseDataClass
import cdbol.br.com.clubedabola.model.ItemGoalkeeper
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView
import cdbol.br.com.clubedabola.view.*

interface NovaContratacaoContract {

    interface View : BaseMvpView {
        fun enableBtn(b: Boolean)
        fun close()

        fun showSimpleAlert(context: Context, message: String)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun tituloTela(name: String)
        fun tipoContratacao(descricao: String)
        fun convertDateText(year: Int, month: Int, dayOfMonth: Int)
        fun convertHourStartText(hourOfDay: Int, minute: Int, text: String)
        //fun convertHourFinishText(hourOfDay: Int, minute: Int, text: String)
        fun buildDuration(item: String)
        fun createMatch()
        fun bindViews(itemMatch: ItemViewLabel?, itemGender: ItemViewLabel?, itemPlace: ItemViewLabel?, itemDate: ItemViewLabel?,
                      itemStart: ItemViewLabel?, itemEnd: ItemViewLabel?, itemObs: ItemViewObservation?, itemChangeType: ItemViewLabelAlterar?, itemCard: ItemCartao, itemGoalkeeper: ItemGoleiro)

        fun buildMatchType(item: String)
        fun buildGender(item: String)
        fun buildAddress(item: Address?)
        fun buildCvc(item: String)
        fun buildGoalkeeper(choosed: ItemGoalkeeper)
    }

}
