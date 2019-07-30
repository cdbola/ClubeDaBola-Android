package cdbol.br.com.clubedabola.screens.ajuda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import cdbol.br.com.clubedabola.R

class AjudaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajuda)

        val toolBar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolBar!!)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

    }
}
