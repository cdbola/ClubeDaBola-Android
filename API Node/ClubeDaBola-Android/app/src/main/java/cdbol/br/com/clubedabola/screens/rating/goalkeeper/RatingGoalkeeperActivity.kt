package cdbol.br.com.clubedabola.screens.rating.goalkeeper

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.RatingMatch
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.rating.RatingContract
import cdbol.br.com.clubedabola.screens.rating.RatingPresenter
import kotlinx.android.synthetic.main.activity_rating_hirer.*
import kotlinx.android.synthetic.main.toollbar.view.*

class RatingGoalkeeperActivity : BaseMvpActivity<RatingContract.View, RatingContract.Presenter>() {

    override var mPresenter: RatingContract.Presenter = RatingPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating_goalkeeper)

        initToolBar()

        btn_send_rating.setOnClickListener { callPutRatingMatch() }
    }


    private fun initToolBar() {

        toolbar_close!!.toolbar_title.text = getString(R.string.title_rating)
        setSupportActionBar(toolbar_close as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)

        (toolbar_close as Toolbar).setNavigationOnClickListener { onBackPressed() }
    }

    fun callPutRatingMatch(){

        showFullProgressbar()
        var id = intent.extras.get("id") as String

        var rating = rb_second.rating



        var ratingRequest = RatingMatch(rating.toDouble(), id)

        ApiManager.putRatingMatch(ratingRequest)
                .subscribe ({
                    hideFullProgressbar()
                    Log.d("Success avaliar", it.mensagem)
                    setResult(Activity.RESULT_OK)
                    finish()

                }, {hideFullProgressbar()
                    Log.d("Error avaliar", it.message)})
    }
}

