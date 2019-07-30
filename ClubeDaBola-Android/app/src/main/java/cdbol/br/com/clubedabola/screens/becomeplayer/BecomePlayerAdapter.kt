package cdbol.br.com.clubedabola.screens.becomeplayer

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cdbol.br.com.clubedabola.screens.becomeplayer.bank.GoalkeeperBankFragment
import cdbol.br.com.clubedabola.screens.becomeplayer.contact.GoalkeeperContactFragment
import cdbol.br.com.clubedabola.screens.becomeplayer.size.GoalkeeperSizeFragment


class BecomePlayerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    companion object {
        const val NUM_PAGES = 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> GoalkeeperSizeFragment()
            1 -> GoalkeeperContactFragment()
            else ->
                GoalkeeperBankFragment()
        }

    }

    override fun getCount(): Int {
        return NUM_PAGES
    }
}