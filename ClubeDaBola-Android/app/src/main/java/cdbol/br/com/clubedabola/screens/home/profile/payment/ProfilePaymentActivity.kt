package cdbol.br.com.clubedabola.screens.home.profile.payment

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.cartao.AdicionarCartaoActivity
import cdbol.br.com.clubedabola.screens.cartao.AdicionarCartaoPresenter
import cdbol.br.com.clubedabola.screens.customer.CustomerActivity
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toollbar.view.*

class ProfilePaymentActivity : BaseMvpActivity<PaymentContract.View, PaymentContract.Presenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        initToolBar()

        tv_add_card.setOnClickListener {
            var intent = Intent(this, AdicionarCartaoActivity::class.java)
            startActivity(intent)
        }
//        rl_payment.layoutManager = LinearLayoutManager(this)
//        rl_payment.adapter = PaymentAdapter {
//
//        }

    }

    override var mPresenter: PaymentContract.Presenter = PaymentPresenter()


    private fun initToolBar() {
        toolbar_payment!!.toolbar_title.text = getString(R.string.title_payment)
        setSupportActionBar(toolbar_payment as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        (toolbar_payment as Toolbar).setNavigationOnClickListener { onBackPressed() }
    }

}
