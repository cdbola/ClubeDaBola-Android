package cdbol.br.com.clubedabola.screens.becomeplayer

import android.location.Address
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView
import cdbol.br.com.clubedabola.screens.becomeplayer.bank.BankData


interface BecomePlayerContract {

    interface View : BaseMvpView {

        fun mainPresenter(): BecomePlayerContract.Presenter
        fun onNext(b: Boolean)
        fun becamePlayer(hirerId: HirerId)
        fun showLoading()
        fun hideLoading()

    }

    interface Presenter : BaseMvpPresenter<BecomePlayerContract.View> {

        fun saveGender(gender:String)
        fun saveGloveSize(gloveSize:String)
        fun saveTshirtSize(tshirSize:String)
        fun saveAddress(address: Address?)
        fun savePhone(phone: String)
        fun saveBankData(bankData: BankData)

        fun buildDataPlayer(type: String)


    }

    interface PageView {

         fun enableNextPosition()
    }
}