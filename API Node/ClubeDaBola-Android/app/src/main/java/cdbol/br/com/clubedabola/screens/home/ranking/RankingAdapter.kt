package cdbol.br.com.clubedabola.screens.home.ranking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.Hired
import kotlinx.android.synthetic.main.item_ranking.view.*


class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {


    private var rankingList: List<Hired>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {

        return RankingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false))
    }


    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val hired = rankingList!![position]
        holder.bind(hired, position)

    }

    override fun getItemCount(): Int {
        return if (rankingList != null) rankingList!!.size else 0
    }

    fun notifyAdapter(hiredList: List<Hired>?) {
        this.rankingList = hiredList
        notifyDataSetChanged()
    }

    class RankingViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


        fun bind(hired: Hired, pos: Int) {


            itemView.tv_order.text = hired.orderNum.toString()

            itemView.tv_name.text = hired.nome
            itemView.tv_city.text = """${hired.endereco.lagradouro} ${hired.endereco.numero}"""
            itemView.tv_num_games.text = hired.partidas.toString()
            itemView.tv_points.text = hired.media.toString() + " pts"

        }

    }

}
