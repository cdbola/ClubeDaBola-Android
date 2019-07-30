
package cdbol.br.com.clubedabola.screens.home.profile.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import cdbol.br.com.clubedabola.R

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        initToolBar()


    }

    private fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toolbar_edit_profile)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var title = toolBar.findViewById<TextView>(R.id.toolbar_title)

        title.text = getString(R.string.edt_title_change_pass)

//        val back = toolBar.findViewById<ImageButton>(R.id.toolbar_back)
//
//        back?.setOnClickListener {
//            onBackPressed()
//        }
    }
}
