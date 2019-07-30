package cdbol.br.com.clubedabola.screens.home.profile.notification

import android.os.Bundle
import android.support.v7.widget.Toolbar
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.home.MatchNotificationContract
import cdbol.br.com.clubedabola.screens.home.MatchNotificationPresenter
import kotlinx.android.synthetic.main.activity_match_notification.*
import kotlinx.android.synthetic.main.toolbar_save.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MatchNotificationActivity : BaseMvpActivity<MatchNotificationContract.View, MatchNotificationContract.Presenter>(), MatchNotificationContract.View {


    override var mPresenter: MatchNotificationContract.Presenter = MatchNotificationPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_notification)
        initToolBar()
        mPresenter.handleSeekBar(seekBar, tv_radius_value)
        mPresenter.handleSwitch(switch_notification)
    }

    private fun initToolBar() {
        toolbar_notification!!.toolbar_title.text = getString(R.string.title_match_notification)
        setSupportActionBar(toolbar_notification as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        (toolbar_notification as Toolbar).setNavigationOnClickListener { onBackPressed() }

        toolbar_notification!!.toolbar_save.setOnClickListener { mPresenter.callUpdatePlayer() }
    }

    override fun successUpdated(hirerId: HirerId){
        saveHirerToPreferences(hirerId)
        showAlert()

    }

    fun showAlert() {
        alert("Alteração realizada!") {
            yesButton {
                finish()
            }

        }.show()
    }

}
