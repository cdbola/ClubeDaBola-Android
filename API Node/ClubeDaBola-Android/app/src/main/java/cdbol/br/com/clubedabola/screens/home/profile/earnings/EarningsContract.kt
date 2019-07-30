package cdbol.br.com.clubedabola.screens.home

import android.content.Context
import android.widget.Button
import android.widget.TextView
import cdbol.br.com.clubedabola.model.WalletDetail
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView


interface EarningsContract {

    interface View : BaseMvpView {


        fun showSimpleAlert(context: Context, message: String)

        fun showAlert()

    }

    interface Presenter : BaseMvpPresenter<EarningsContract.View> {

        fun walletDetail(walletDetail: WalletDetail)

        fun bindViews(tv_amount: TextView, tv_amount_pending: TextView, tv_msg: TextView, btn_withdraw: Button)

    }
}