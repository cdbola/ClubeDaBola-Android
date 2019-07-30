package cdbol.br.com.clubedabola.screens.local

import android.content.Intent
import android.widget.AutoCompleteTextView
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView

interface PlaceContract {

    interface View : BaseMvpView {
        fun returnResult(intent: Intent)

    }

    interface Presenter : BaseMvpPresenter<View> {
        fun geoLocate(places_autocomplete: AutoCompleteTextView)

    }

}
