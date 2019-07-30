package cdbol.br.com.clubedabola.mvp

import android.content.Context
import android.support.annotation.StringRes
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.model.HirerIdObj

/**
 * Created by andrewkhristyan on 10/2/16.
 */
interface BaseMvpView {

    fun getContext(): Context

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)

    fun configuraToolBar(tituloTela: String)

    fun hideFullProgressbar()

    fun showFullProgressbar()

    fun getHirer():HirerIdObj
    fun saveHirerToPreferences(hirerId: HirerId)

}
