package cdbol.br.com.clubedabola.screens.becomeplayer.contact


import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.screens.becomeplayer.BecomePlayerContract
import cdbol.br.com.clubedabola.screens.local.LocalActivity
import cdbol.br.com.clubedabola.utils.PhoneNumberFormatType
import cdbol.br.com.clubedabola.utils.PhoneNumberFormatter
import kotlinx.android.synthetic.main.fragment_goalkeeper_contact.*
import java.lang.ref.WeakReference


class GoalkeeperContactFragment : Fragment(), BecomePlayerContract.PageView {


    private var REQUEST_ADDRESS = 2000
    private var mainView: BecomePlayerContract.View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goalkeeper_contact, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainView = activity as BecomePlayerContract.View

        val phoneFormatter = PhoneNumberFormatter(WeakReference(tv_select_phone), PhoneNumberFormatType.PT_BR)
        tv_select_phone.addTextChangedListener(phoneFormatter)

        tv_select_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length > 11) {
                    mainView!!.mainPresenter().savePhone(tv_select_phone.text.toString())
                }
                enableNextPosition()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        tv_address_selected.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableNextPosition()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        container_address.setOnClickListener {
            val intent = Intent(activity, LocalActivity::class.java)
            startActivityForResult(intent, REQUEST_ADDRESS)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            enableNextPosition()
        }
    }

    override fun enableNextPosition() {

        if (tv_select_phone.text.isNotEmpty() && tv_address_selected.text.isNotEmpty()) {
            mainView!!.onNext(true)
        } else {
            mainView!!.onNext(false)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
//                val address = data?.getStringExtra("address")
//                val moshi = Moshi.Builder()
//                        .add(KotlinJsonAdapterFactory())
//                        .build()
//                val jsonAdapter = moshi.adapter<LocResult>(LocResult::class.java)
//
//                val parseAddress = jsonAdapter.fromJson(address)
//
//                tv_address_selected.text = parseAddress!!.results[0].formatted_address
//
//                mainView!!.mainPresenter().saveAddress(parseAddress!!.results[0].formatted_address)

                var address: Address? = null
                 address = data?.getParcelableExtra("address")
                mainView!!.mainPresenter().saveAddress(address)
                tv_address_selected.text = address!!.getAddressLine(0)
                enableNextPosition()

            }

        }
    }
}
