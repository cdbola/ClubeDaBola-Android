package cdbol.br.com.clubedabola.mvp

import android.app.Dialog
import android.app.FragmentManager
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.model.HirerIdObj
import cdbol.br.com.clubedabola.screens.home.FragmentAbstrato
import cdbol.br.com.clubedabola.utils.PreferencesString
import cdbol.br.com.clubedabola.view.FullProgressDialog
import rx.functions.Action1

/**
 * Created by andrewkhristyan on 10/2/16.
 */
abstract class BaseMvpActivity<in V : BaseMvpView, T : BaseMvpPresenter<V>>
    : AppCompatActivity(), BaseMvpView {

    private var dialog: ProgressDialog? = null
    private var fullProgress: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getContext(): Context = this

    protected abstract var mPresenter: T

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(srtResId: Int) {
        Toast.makeText(this, srtResId, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun configuraToolBar(tituloTela : String) {

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar!!)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        val title = toolBar.findViewById<TextView>(R.id.toolbar_title)
        title.setText(tituloTela)

        val cancelar = toolBar.findViewById<TextView>(R.id.toolbar_cancelar)
        cancelar.setOnClickListener(View.OnClickListener { finish() })

    }

    override fun saveHirerToPreferences(hirerId: HirerId){
        PreferencesString.instance.putObj("hirer", hirerId)

    }

    override fun getHirer():HirerIdObj{
        return PreferencesString.instance.getObj("hirer", HirerIdObj()) as HirerIdObj
    }

    fun callGetHirerId(){
        ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                .subscribe {saveHirerToPreferences(it) }
    }

    fun trocaFragmento(fragment: Fragment) {

        /*
         * Remove as pilhas de fragmentos para que não haja a
         * possibilidade de navegação entre os itens do
         * BottomNavigation, respeitando assim uma das regras
         * de negócio deste componente.
         * */
        supportFragmentManager
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.rl_container, fragment, FragmentAbstrato.KEY)
                .commit()
    }

    fun showSimpleAlert(context: Context, message: String){
        FullProgressDialog.showAlert(context, message)
    }

    override fun showFullProgressbar(){
        fullProgress = FullProgressDialog.fullProgressDialog(this)
        fullProgress!!.show()
    }

    override fun hideFullProgressbar(){
        fullProgress!!.dismiss()
    }

    fun showLoadingDialog(message: String, title: String): ProgressDialog {
        dialog = ProgressDialog(this)
        dialog!!.setMessage(message)
        dialog!!.setTitle(title)
        dialog!!.show()
        return dialog!!
    }

    fun hideLoadingDialog() {
        dialog!!.dismiss()

    }

}