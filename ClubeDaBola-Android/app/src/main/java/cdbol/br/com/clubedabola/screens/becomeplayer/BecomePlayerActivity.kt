package cdbol.br.com.clubedabola.screens.becomeplayer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_become_player.*

class BecomePlayerActivity : BaseMvpActivity<BecomePlayerContract.View,
        BecomePlayerContract.Presenter>(), BecomePlayerContract.View {


    var type : String = ""
    private lateinit var pagerAdapter: BecomePlayerAdapter

    var next: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_player)

        initToolBar()

        pagerAdapter = BecomePlayerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.setSwipePagingEnabled(false)
        viewPager.offscreenPageLimit = 3
        viewPager.currentItem = 0


        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {
                    2 -> next!!.text = getString(R.string.btn_done)
                    else -> next!!.text = getString(R.string.btn_next)
                }
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    override var mPresenter: BecomePlayerContract.Presenter = BecomePlayerPresenter()

    override fun onNext(b: Boolean) {
        next!!.isEnabled = b
    }

    override fun mainPresenter(): BecomePlayerContract.Presenter {
        return mPresenter
    }

    fun initToolBar() {

        type  = intent.getStringExtra("type")

        val toolBar = findViewById<Toolbar>(R.id.toolbar_player)
        setSupportActionBar(toolBar!!)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        var title = toolBar.findViewById<TextView>(R.id.toolbar_title)

        if (type == "G"){
            title.text = getString(R.string.title_new_goalkeeper)

        }else{
            title.text =  getString(R.string.title_new_referee)
        }
        next = toolBar.findViewById(R.id.toolbar_next)

        next!!.setOnClickListener {
            if (viewPager.currentItem < pagerAdapter.count - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }else{
                showLoading()
                mPresenter.buildDataPlayer(type)
            }

        }

        val back = toolBar.findViewById<ImageButton>(R.id.toolbar_back)

        back?.setOnClickListener {
            onBackPressed()
        }
    }

    override fun becamePlayer(hirerId: HirerId) {

        saveHirerToPreferences(hirerId)
        var intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    override fun onBackPressed() {

        if (viewPager.currentItem !== 0) {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)

        } else {
            super.onBackPressed()
        }

    }



    override fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

}
