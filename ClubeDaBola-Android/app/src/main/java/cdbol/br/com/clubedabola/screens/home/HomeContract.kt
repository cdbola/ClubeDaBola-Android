package cdbol.br.com.clubedabola.screens.home

import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView


interface HomeContract {

    interface View : BaseMvpView {



    }

    interface Presenter : BaseMvpPresenter<HomeContract.View> {


    }
}