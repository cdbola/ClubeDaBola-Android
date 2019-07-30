package cdbol.br.com.clubedabola.screens.local

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.AutoCompleteTextView
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors


class PlacePresenter : BaseMvpPresenterImpl<PlaceContract.View>(),
        PlaceContract.Presenter {
    override fun geoLocate(places_autocomplete: AutoCompleteTextView) {

        val searchString = places_autocomplete.text.toString()

        val geocoder = Geocoder(mView?.getContext())
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
        } catch (e: IOException) {
            Log.e("Place", "geoLocate: IOException: " + e.message)
        }


        if (list.isNotEmpty()) {
            val address = list[0]
            Log.d("Place", "geoLocate: found a location: " + address.toString())
            var intent = Intent()
            intent.putExtra("address", address)
            mView?.returnResult(intent)

//            val executor = Executors.newScheduledThreadPool(3)
//            doAsync(executorService = executor) {
//                val result = URL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyAl-Bc4ABRvsR3J-KQLaInNN2beuuDBWEM&address=$searchString").readText()
//                uiThread {
//                    var intent = Intent()
//                    intent.putExtra("address", result)
//
//                    mView?.returnResult(intent)
//                }
//            }

        }
    }



}