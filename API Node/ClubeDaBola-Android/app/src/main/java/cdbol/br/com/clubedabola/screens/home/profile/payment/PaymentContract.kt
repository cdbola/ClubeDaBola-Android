package cdbol.br.com.clubedabola.screens.home.profile.payment

import android.location.Address
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView
import cdbol.br.com.clubedabola.screens.becomeplayer.bank.BankData


interface PaymentContract {

    interface View : BaseMvpView {



    }

    interface Presenter : BaseMvpPresenter<PaymentContract.View> {



    }
}