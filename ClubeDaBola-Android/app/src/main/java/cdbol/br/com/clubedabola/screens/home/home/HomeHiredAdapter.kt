package cdbol.br.com.clubedabola.screens.home.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.RecentMatch


class HomeHiredAdapter(var matchList: List<RecentMatch>) : RecyclerView.Adapter<HomeHiredAdapter.HomeViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_hired, parent, false))
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return matchList.size
    }


    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }

}
