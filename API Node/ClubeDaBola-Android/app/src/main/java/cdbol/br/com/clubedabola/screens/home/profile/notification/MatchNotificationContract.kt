package cdbol.br.com.clubedabola.screens.home

import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import cdbol.br.com.clubedabola.model.HirerId
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView


interface MatchNotificationContract {

    interface View : BaseMvpView {

        fun successUpdated(hirerId: HirerId)
    }

    interface Presenter : BaseMvpPresenter<MatchNotificationContract.View> {
        fun handleSeekBar(seekBar: SeekBar?, tv_radius_value: TextView)
        fun handleSwitch(switch_notification: Switch?)
        fun callUpdatePlayer()
    }
}