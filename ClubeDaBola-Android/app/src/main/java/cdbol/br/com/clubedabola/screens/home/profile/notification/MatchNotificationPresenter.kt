package cdbol.br.com.clubedabola.screens.home

import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.api.GeneralErrorHandler
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.PlayerUpdateRequest
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.utils.PreferencesString
import rx.functions.Action1

class MatchNotificationPresenter : BaseMvpPresenterImpl<MatchNotificationContract.View>(),
        MatchNotificationContract.Presenter {

    private var switchNotification: Switch? = null
    private var tvRadiusValue: TextView? = null
    private var seekBar: SeekBar? = null
    private var isOn: Boolean = false
    private var progressChangedValue = 0

    override fun handleSwitch(switch_notification: Switch?) {
        switchNotification = switch_notification

        if (mView!!.getHirer().goleiro != null) setSwitch(mView!!.getHirer().goleiro!!.notificacoes)

        if (mView!!.getHirer().arbitro != null) setSwitch(mView!!.getHirer().arbitro!!.notificacoes)

        switch_notification!!.setOnCheckedChangeListener { _, isChecked ->
            isOn = isChecked
        }
    }

    override fun handleSeekBar(seekBar: SeekBar?, tv_radius_value: TextView) {

        this.tvRadiusValue = tv_radius_value
        this.seekBar = seekBar

        if (mView!!.getHirer().goleiro != null) setupRadiusValue(mView!!.getHirer().goleiro!!.coord)
        if (mView!!.getHirer().arbitro != null) setupRadiusValue(mView!!.getHirer().arbitro!!.coord)

        seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress
                setupRadiusValue(progressChangedValue.toDouble())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar!!.progress = progressChangedValue
            }
        })
    }

    private fun setSwitch(value: Boolean) {
        switchNotification!!.isChecked = value
        isOn = value
    }

    private fun setupRadiusValue(progressChangedValue: Double) {
        this.progressChangedValue = progressChangedValue.toInt()
        tvRadiusValue!!.text = String.format(mView!!.getContext().resources.getString(R.string.text_radius_value), progressChangedValue.toInt())
        seekBar!!.progress = progressChangedValue.toInt()

    }

    override fun callUpdatePlayer() {

        var id: String = ""

        if (mView!!.getHirer().goleiro != null || mView!!.getHirer().arbitro != null) {
            if (mView!!.getHirer().goleiro != null) {
                id = mView!!.getHirer().goleiro!!._id
            } else if (mView!!.getHirer().arbitro != null) {
                id = mView!!.getHirer().arbitro!!._id
            }
            var dataPlayer = PlayerUpdateRequest(
                    id,
                    isOn,
                    progressChangedValue
            )

            if (mView!!.getHirer().goleiro != null) {
                ApiManager.updatePlayer(dataPlayer!!)
                        .subscribe(Action1 { getHirerId()},
                                GeneralErrorHandler(mView, true) { _, _, _ -> })
            } else {
                ApiManager.updateReferee(dataPlayer!!)
                        .subscribe(Action1 {getHirerId() },
                                GeneralErrorHandler(mView, true) { _, _, _ -> })

            }


        }
    }

    private fun getHirerId(){
        ApiManager.getHirerById(PreferencesString.instance.getString("hirerId")!!)
                .subscribe(Action1 { mView!!.successUpdated(it)},
                        GeneralErrorHandler(mView, false) { _, _, _ -> })
    }


}
