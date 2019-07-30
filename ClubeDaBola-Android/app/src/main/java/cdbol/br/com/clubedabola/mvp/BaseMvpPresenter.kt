package cdbol.br.com.clubedabola.mvp

/**
 * Created by andrewkhristyan on 10/2/16.
 */
interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()

}