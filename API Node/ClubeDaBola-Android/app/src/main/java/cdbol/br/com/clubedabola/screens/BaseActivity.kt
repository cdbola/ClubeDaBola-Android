package cdbol.br.com.clubedabola.screens

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import cdbol.br.com.clubedabola.R

abstract class BaseActivity : AppCompatActivity() {



    fun configuraToolBar(tituloTela : String) {

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar!!)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        val title = toolBar.findViewById<TextView>(R.id.toolbar_title)
        title.setText(tituloTela)

        val cancelar = toolBar.findViewById<TextView>(R.id.toolbar_cancelar)
        cancelar.setOnClickListener(View.OnClickListener { finish() })

    }
}
