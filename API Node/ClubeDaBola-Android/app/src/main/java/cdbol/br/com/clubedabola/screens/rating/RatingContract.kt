package cdbol.br.com.clubedabola.screens.rating

import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView


interface RatingContract {

    interface View : BaseMvpView {



    }

    interface Presenter : BaseMvpPresenter<RatingContract.View> {


    }
}