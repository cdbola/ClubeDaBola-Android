package cdbol.br.com.clubedabola.screens.local

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.screens.localmanual.LocalManualActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_local.*
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity


class LocalActivity() : GoogleApiClient.OnConnectionFailedListener,  BaseMvpActivity<PlaceContract.View,
        PlaceContract.Presenter>(), PlaceContract.View {


    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    private var REQUEST_LOCAL = 1000

    private val latLngBounds = LatLngBounds(
            LatLng(-72.89583, -34.80861), LatLng(-33.69111, 2.81972))

    private var autocompleteAdapter: PlaceAutocompleteAdapter? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    var done: TextView? = null

    override var mPresenter: PlaceContract.Presenter = PlacePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)

        mPresenter.attachView(this)

       // configuraToolBar("Local")

        initToolBar()
        initPlace()

        txt_adicionar_manualmente.setOnClickListener {
            val intent = Intent(this, LocalManualActivity::class.java)
            startActivityForResult(intent,REQUEST_LOCAL)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_LOCAL){
            if(resultCode == Activity.RESULT_OK){
                var address: Address? = null
                address = data?.getParcelableExtra("address")
                var intent = Intent()
                intent.putExtra("address", address)
                returnResult(intent)
            }
        }


    }

    fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val cancel = toolBar.findViewById<Button>(R.id.toolbar_cancelar)

        cancel.setOnClickListener {
            finish()
        }

        done = toolBar.findViewById(R.id.toolbar_done)

        done?.setOnClickListener {
           if (places_autocomplete.text.isNotEmpty()){

               mPresenter.geoLocate(places_autocomplete)

           }
        }
    }

    override fun returnResult(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    private fun initPlace(){

        mGoogleApiClient = GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build()

        autocompleteAdapter = PlaceAutocompleteAdapter(this, mGoogleApiClient, latLngBounds, null)

        places_autocomplete.setAdapter(autocompleteAdapter)

        places_autocomplete.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                done?.isEnabled = s.isNotEmpty()
            }
        })
    }

}
