package cdbol.br.com.clubedabola.screens.becomeplayer

import android.location.Address
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.*
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.screens.becomeplayer.bank.BankData
import cdbol.br.com.clubedabola.utils.PreferencesString
import rx.functions.Action1

class BecomePlayerPresenter : BaseMvpPresenterImpl<BecomePlayerContract.View>(),
        BecomePlayerContract.Presenter {

    private var gender : String = ""
    private var gloveSize : String = ""
    private var tshirtSize : String = ""
   // private var address : String = ""
    private var address: Address? = null
    private var phone : String = ""
    private var bankData: BankData? = null


    override fun saveGender(gender: String) {
       this.gender = gender
    }

    override fun saveGloveSize(gloveSize: String) {
        this.gloveSize = gloveSize
    }

    override fun saveTshirtSize(tshirtSize: String) {
        this.tshirtSize = tshirtSize
    }

    override fun saveAddress(address: Address?) {
        this.address = address
    }

    override fun savePhone(phone: String) {
        this.phone = phone
    }


    override fun saveBankData(bankData: BankData) {
       this.bankData = bankData
    }

    override fun buildDataPlayer(type: String) {
         var dataPlayer = GoalKeeperRequest(
                 PreferencesString.instance.getString("hirerId")!!,
                this.gender,
                this.gloveSize,
                this.tshirtSize,
                 BankInfoGoalKeeper(this.bankData!!.cpf, this.bankData!!.bank, this.bankData!!.agency,this.bankData!!.account),
                 AddressGoalKeeper( this.address!!.getAddressLine(0),"","","", LocGoalKeeper(arrayOf(this.address!!.longitude,address!!.latitude))),
                this.phone,
                 "",
                 PreferencesString.instance.getString("nickName")!!+ ".png",
                 true,
                 10
        )

        if (type == "G"){
            ApiManager.becomePlayer(dataPlayer!!)
                    .subscribe(Action1 {postDigitalWallet() },
                            GeneralErrorHandler(mView, false) { _, _, _ -> })
        }else{
            ApiManager.becomeReferee(dataPlayer!!)
                    .subscribe(Action1 {postDigitalWallet() },
                            GeneralErrorHandler(mView, false) { _, _, _ -> })
        }

    }

    private fun postDigitalWallet(){
        var request = DigitalWalletResquest(PreferencesString.instance.getString("hirerId")!!)
        ApiManager.postDigitalWallet(request)
                .subscribe(Action1 { getHirerId()},
                        GeneralErrorHandler(mView, false) { _, _, _ -> })
    }

    private fun getHirerId(){
        ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                .subscribe(Action1 { mView!!.becamePlayer(it)},
                        GeneralErrorHandler(mView, false) { _, _, _ -> })
    }

}
