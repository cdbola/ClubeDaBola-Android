package cdbol.br.com.clubedabola.screens.home.profile.payment

import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.DigitalWalletResquest
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.PreferencesString
import rx.functions.Action1

class PaymentPresenter : BaseMvpPresenterImpl<PaymentContract.View>(),
        PaymentContract.Presenter {





    private fun postDigitalWallet(){
        var request = DigitalWalletResquest(PreferencesString.instance.getString("hirerId")!!)
        ApiManager.postDigitalWallet(request)
                .subscribe(Action1 { getHirerId()},
                        GeneralErrorHandler(mView, false) { _, _, _ -> })
    }

    private fun getHirerId(){
        ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                .subscribe(Action1 {},
                        GeneralErrorHandler(mView, false) { _, _, _ -> })
    }

}
