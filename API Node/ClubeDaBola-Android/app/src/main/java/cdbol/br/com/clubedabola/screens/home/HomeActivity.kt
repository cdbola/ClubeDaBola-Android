package cdbol.br.com.clubedabola.screens.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.MenuItem
import android.view.View
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.Push
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.home.historic.HistoricoFragment
import cdbol.br.com.clubedabola.screens.home.home.HomeFragment
import cdbol.br.com.clubedabola.screens.home.profile.PerfilFragment
import cdbol.br.com.clubedabola.screens.home.ranking.RankingFragment
import cdbol.br.com.clubedabola.utils.PreferencesString
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.toollbar.view.*
import org.jetbrains.anko.startActivity
import rx.functions.Action1

class HomeActivity : BaseMvpActivity<HomeContract.View, HomeContract.Presenter>(),
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener, EditProfileContract.View, HomeContract.View {

    var REQUEST_BECAME = 3000
     var REQUEST_CREATE_MATCH = 9000
    override var mPresenter: HomeContract.Presenter = HomePresenter()


    override fun onNavigationItemReselected(item: MenuItem) {
    //    onNavigationItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        /*val  fragmento: Fragment


        //toolbar.title = titulo

        when (item.itemId) {
            R.id.nav_home -> {
                fragmento = HomeFragment()
                titulo = HomeFragment.TITULO
            }

            R.id.nav_blog -> {
                fragmento = BlogFragment()
                titulo = BlogFragment.TITULO
            }
            else -> {
                fragmento = HomeFragment()
                titulo = HomeFragment.TITULO
            }
        }
        toolbar.toolbar_title.setText(titulo)

        trocaFragmento( fragmento )*/

        return true

    }

    var titulo: String = HomeFragment.TITULO



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
         when (item.itemId) {
             R.id.nav_home -> {
                 toolbar.toolbar_title.setText(R.string.title_home)
                  trocaFragmento(HomeFragment())

                 return@OnNavigationItemSelectedListener true
             }
             R.id.nav_blog -> {
                 toolbar.toolbar_title.text = "Conteúdo"
                    trocaFragmento(BlogFragment())
                 return@OnNavigationItemSelectedListener true
             }
             R.id.nav_historico -> {
                 toolbar.toolbar_title.text = "Histórico"
                 trocaFragmento(HistoricoFragment())
                 return@OnNavigationItemSelectedListener true
             }
            R.id.nav_classificacao ->{
                toolbar.toolbar_title.text = "Ranking"
                trocaFragmento(RankingFragment())
                 return@OnNavigationItemSelectedListener true
             }
             R.id.nav_perfil ->{
                 toolbar.toolbar_title.text = "Perfil"
                 trocaFragmento(PerfilFragment())

                 return@OnNavigationItemSelectedListener true
             }

         }
         false
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.itemIconTintList = null

        PreferencesString.getInstance(this)

        if (!PreferencesString.instance.getString("hirerId").isNullOrEmpty()) {

            ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                    .subscribe ({
                        saveHirerToPreferences(it)
                        postNotification()

                    }, {

                        Log.d("Erro contratante id", it.message)
                    })

        }


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        titulo = savedInstanceState?.getString(FragmentAbstrato.TITULO) ?: titulo



        /*
         * Caso seja a primeira vez que o onCreate() esteja sendo
         * invocado.
         * */
        if (supportFragmentManager.findFragmentByTag(FragmentAbstrato.KEY) == null) {
            trocaFragmento(HomeFragment())
        }

    }

    fun postNotification() {

        val token = FirebaseInstanceId.getInstance().token
        Log.d("Token da App", token)

        var pushRequest = Push(
                getHirer().id,
                token.toString()
        )

        ApiManager.postNotification(pushRequest)
                .subscribe({

                }, {

                    Log.d("Error create push", it.message)
                })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BECAME) {
            if (resultCode == Activity.RESULT_OK) {

                toolbar.toolbar_title.text = "Perfil"
                trocaFragmento(PerfilFragment())

            }
        }else if(requestCode == REQUEST_CREATE_MATCH){
            if (resultCode == Activity.RESULT_OK) {
                trocaFragmento(HomeFragment())
            }
        }
    }

    fun apresentarToolbar(status: Boolean) {
        toolbar.visibility =
                if (status)
                    View.VISIBLE
                else
                    View.GONE
    }

//    private fun trocaFragmento(fragment: Fragment) {
//
//        /*
//         * Remove as pilhas de fragmentos para que não haja a
//         * possibilidade de navegação entre os itens do
//         * BottomNavigation, respeitando assim uma das regras
//         * de negócio deste componente.
//         * */
//        supportFragmentManager
//                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//        supportFragmentManager
//                .beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                .replace(R.id.rl_container, fragment, FragmentAbstrato.KEY)
//                .commit()
//    }

    /*
     * Para que seja possível sempre apresentar o título correto
     * em tela.
     * */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(FragmentAbstrato.TITULO, titulo)
        super.onSaveInstanceState(outState)
    }


}
