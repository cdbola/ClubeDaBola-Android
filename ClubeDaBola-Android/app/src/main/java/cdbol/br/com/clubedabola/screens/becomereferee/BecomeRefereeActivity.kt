package cdbol.br.com.clubedabola.screens.becomereferee

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.screens.becomeplayer.BecomePlayerAdapter
import kotlinx.android.synthetic.main.activity_become_referee.*

class BecomeRefereeActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: BecomePlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_player)

        pagerAdapter = BecomePlayerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.setSwipePagingEnabled(true)


    }
}