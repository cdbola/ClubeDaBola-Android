package cdbol.br.com.clubedabola.screens.home

import android.util.Log
import android.widget.Button
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.WalletDetail
import cdbol.br.com.clubedabola.model.WithDrawRequest
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.PreferencesString
import cdbol.br.com.clubedabola.utils.ViewUtils

class EarningsPresenter : BaseMvpPresenterImpl<EarningsContract.View>(),
        EarningsContract.Presenter {

    private var walletDetail: WalletDetail? = null

    override fun walletDetail(walletDetail: WalletDetail){
        this.walletDetail = walletDetail
    }

    override fun bindViews(tv_amount: TextView, tv_amount_pending: TextView, tv_msg: TextView, btn_withdraw: Button) {


        val msg = String.format(mView!!.getContext().resources.getString(R.string.text_earnings),
                ViewUtils.formatValueCurrency(walletDetail!!.valorTotal.toString()))

        tv_amount.text = ViewUtils.formatValueCurrency(walletDetail!!.valorDisponivel.toString())
        tv_amount_pending.text = ViewUtils.formatValueCurrency(walletDetail!!.valorPendente.toString())
        tv_msg.text = msg

        var withDrawRequest = WithDrawRequest(PreferencesString.instance.getString("hirerId")!!)

        btn_withdraw.setOnClickListener {

            if(walletDetail!!.valorDisponivel > 0){
                mView!!.showFullProgressbar()
                ApiManager.postWithDraw(withDrawRequest)
                        .subscribe ({
                            Log.d("Success resgate", it.message)
                            mView!!.hideFullProgressbar()
                            mView!!.showAlert()



                        }, {
                            mView!!.hideFullProgressbar()
                            mView!!.showSimpleAlert(mView!!.getContext(), it.message.toString())
                            Log.d("Error resgate", it.message)
                        })
            }else{
                mView!!.showSimpleAlert(mView!!.getContext(), "Não há saldo disponível!")
            }

        }
    }

}
