package cdbol.br.com.clubedabola.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.screens.home.HomeActivity
import cdbol.br.com.clubedabola.screens.login.LoginActivity
import cdbol.br.com.clubedabola.utils.PreferencesString


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        PreferencesString.getInstance(this)
        var token: String? = ""

        if (!PreferencesString.instance.getString("token").isNullOrEmpty()) {
            token = PreferencesString.instance.getString("token")!!

            Log.d("info", token)
            Log.d("info", PreferencesString.instance.getString("hirerId"))

        }

        Handler().postDelayed({

            if (!token!!.trim().isEmpty()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            }
        }, 2000)

    }


}
