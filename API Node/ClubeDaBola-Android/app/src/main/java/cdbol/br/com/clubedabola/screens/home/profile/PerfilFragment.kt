package cdbol.br.com.clubedabola.screens.home.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.HirerIdObj
import cdbol.br.com.clubedabola.model.WalletDetail
import cdbol.br.com.clubedabola.screens.becomeplayer.BecomePlayerActivity
import cdbol.br.com.clubedabola.screens.home.FragmentAbstrato
import cdbol.br.com.clubedabola.screens.home.HomeActivity
import cdbol.br.com.clubedabola.screens.home.HomeContract
import cdbol.br.com.clubedabola.screens.home.profile.earnings.EarningsActivity
import cdbol.br.com.clubedabola.screens.home.profile.edit.EditProfileActivity
import cdbol.br.com.clubedabola.screens.home.profile.notification.MatchNotificationActivity
import cdbol.br.com.clubedabola.screens.home.profile.payment.ProfilePaymentActivity
import cdbol.br.com.clubedabola.screens.login.LoginActivity
import cdbol.br.com.clubedabola.screens.webview.WebGenericActivity
import cdbol.br.com.clubedabola.utils.CircleTransform
import cdbol.br.com.clubedabola.utils.MaskUtils
import cdbol.br.com.clubedabola.utils.PreferencesString
import cdbol.br.com.clubedabola.utils.ViewUtils
import com.facebook.login.LoginManager
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertTheme
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_perfil.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.toast
import java.io.Serializable


class PerfilFragment : FragmentAbstrato() {


    companion object {
        @JvmField
        val TITULO = "Perfil"

    }
    private var hirer: HirerIdObj? = null
    var mView : HomeContract.View? = null

    var adapter : ItemProfileAdapter? = null
    private var adapterPlayer: ItemProfilePlayerAdapter? = null

    private var details: WalletDetail? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = activity as HomeContract.View
        hirer = PreferencesString.instance.getObj("hirer", HirerIdObj()) as HirerIdObj

        setupAdapter()

        Picasso.get().load(hirer!!.avatar).placeholder(R.drawable.ic_user).transform(CircleTransform()).into(iv_photo_frag)

        tv_name.text = hirer!!.name

        tv_email.text = hirer!!.email

        btn_edit_profile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)

        }

        callWalletDetail()
    }

    private fun setupAdapter() {

        rl_profile.layoutManager = LinearLayoutManager(activity)
        rl_profile.isNestedScrollingEnabled = false


        if ( hirer!!.goleiro != null && hirer!!.arbitro == null || hirer!!.goleiro == null && hirer!!.arbitro != null ){

            adapter = ItemProfileAdapter(createItems()) {

                when (it) {
                    0 -> openEarnings()
                    1 -> openPayment()
                    2 -> openWhats()
                    4 -> openNotification()
                    5 -> openHelp()
                    6 -> logout()
                    7 -> openBanner()
                }
            }

            rl_profile.adapter = adapter

        }else{

            adapterPlayer = ItemProfilePlayerAdapter(createItemsPlayer()) {

                when (it) {
                    0 -> openPayment()
                    1 -> openWhats()
                    3 -> openNotification()
                    4 -> showAlert()
                    5 -> openHelp()
                    6 -> logout()
                    7 -> openBanner()
                }
            }

            rl_profile.adapter = adapterPlayer

        }


    }


    private fun leavePlayer() {

    }

    private fun openHelp(){
        startActivity(intentFor<WebGenericActivity>("url" to  "https://cdbola.com.br" ))
    }

    private fun openBanner(){
        startActivity(intentFor<WebGenericActivity>("url" to  "https://poker.esp.br" ))
    }

    private fun openPayment(){
        val intent = Intent(activity, ProfilePaymentActivity::class.java)
        startActivity(intent)
    }


    private fun openNotification() {
        val intent = Intent(activity, MatchNotificationActivity::class.java)
        startActivity(intent)
    }

    private fun openEarnings() {

        if(details != null)
            startActivity(intentFor<EarningsActivity>("details" to details as Serializable))
    }

    private fun openWhats() {
        val pm = activity!!.packageManager
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"
            val text = activity!!.getString(R.string.msg_whats)

            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)

            waIntent.`package` = "com.whatsapp"

            waIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(waIntent, ""))

        } catch (e: PackageManager.NameNotFoundException) {

            activity!!.toast("Ops!! no Whats")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logout() {

        //            ApiManager.logout(sharedPreference!!.getString("token",""))
//                    .doOnError { Log.d("error", "logout") }
//                    .subscribe(Action1 { logout()  })

        PreferencesString.instance.remove("token")
        PreferencesString.instance.remove("hirerId")
        PreferencesString.instance.remove("nickName")
        PreferencesString.instance.remove("hirer")

        LoginManager.getInstance().logOut()

        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showAlert() {

        val alert = AlertView(getString(R.string.array_item_profile_5), "", AlertStyle.DIALOG)
        alert.setTheme(AlertTheme.LIGHT)

        alert.addAction(AlertAction("Goleiro", AlertActionStyle.NEGATIVE) { action ->
            val intent = Intent(activity, BecomePlayerActivity::class.java)
            intent.putExtra("type", "G")
            activity?.startActivityForResult(intent, HomeActivity().REQUEST_BECAME)
        })

        alert.addAction(AlertAction("Árbitro", AlertActionStyle.NEGATIVE) { action ->

            val intent = Intent(activity, BecomePlayerActivity::class.java)
            intent.putExtra("type", "J")
            activity?.startActivityForResult(intent, HomeActivity().REQUEST_BECAME)
        })

        alert.addAction(AlertAction("Cancelar", AlertActionStyle.NEGATIVE) { action ->

        })

        alert.show(activity as AppCompatActivity)

    }

    private fun callWalletDetail(){
        ApiManager.getWalletDetails(hirer!!.id)
                .subscribe ({
                    if(it.sucesso){
                        details = it
                        adapter!!.notifyAdapter(createItems())
                    }


                }, {

                    Log.d("Erro", it.message)
                })
    }

    private fun createItems(): ArrayList<ItemProfile> {

        val items = ArrayList<ItemProfile>()
        if (details != null)
            items.add(ItemProfile(getString(R.string.array_item_profile_0), ViewUtils.formatValueCurrency(details!!.valorDisponivel.toDouble()))) else
            items.add(ItemProfile(getString(R.string.array_item_profile_0), ""))

        items.add(ItemProfile(getString(R.string.array_item_profile_1), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_2), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_3), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_4), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_6), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_7), ""))

        return items
    }

    private fun createItemsPlayer(): ArrayList<ItemProfile> {

        val items = ArrayList<ItemProfile>()
        items.add(ItemProfile(getString(R.string.array_item_profile_1), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_2), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_3), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_4), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_5), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_6), ""))
        items.add(ItemProfile(getString(R.string.array_item_profile_7), ""))

        return items
    }


}