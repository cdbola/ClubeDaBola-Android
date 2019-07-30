package cdbol.br.com.clubedabola.screens.home.profile.earnings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.WalletDetail
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.home.EarningsContract
import cdbol.br.com.clubedabola.screens.home.EarningsPresenter
import kotlinx.android.synthetic.main.activity_earnings.*
import kotlinx.android.synthetic.main.toollbar.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class EarningsActivity : BaseMvpActivity<EarningsContract.View, EarningsContract.Presenter>(), EarningsContract.View {

    var adapter: EarningsAdapter? = null

    override var mPresenter: EarningsContract.Presenter = EarningsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings)

        var details = intent.extras.get("details") as WalletDetail
        mPresenter.walletDetail(details)
        mPresenter.bindViews(tv_amount, tv_amount_pending, tv_msg, btn_withdraw)

        initToolBar()
        mPresenter.attachView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EarningsAdapter(details.extrato)
        recyclerView.adapter = adapter


    }


    private fun initToolBar() {
        toolbar_earnings!!.toolbar_title.text = getString(R.string.title_my_earnings)
        setSupportActionBar(toolbar_earnings as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        (toolbar_earnings as Toolbar).setNavigationOnClickListener { onBackPressed() }
    }

    override fun showAlert() {
        alert("Resgate realizado!") {
            yesButton {
                finish()
            }

        }.show()
    }
}
