package cdbol.br.com.clubedabola.screens.home.historic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.HistoricMatch
import cdbol.br.com.clubedabola.model.PassedMatch
import kotlinx.android.synthetic.main.item_historic_hired.view.*
import kotlin.collections.ArrayList


class HistoricAdapter(private val listener: ClickListener) : RecyclerView.Adapter<HistoricAdapter.HomeViewHolder>() {

    private var matchList: List<HistoricMatch>? = null
    private var type: Int? = null


    private val arraylist: ArrayList<PassedMatch>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_historic_hired, parent, false))
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val match = matchList!![position]
        holder.bindView(match, type, listener)

    }


    fun notifyAdapter(matchList: List<HistoricMatch>?, type: Int){
        this.matchList = matchList
        this.type = type
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return if (matchList != null) matchList!!.size else 0
    }

    interface ClickListener {
        fun onClickToRating(id: String)
    }


    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


        fun bindView(match: HistoricMatch, type: Int?, listener: ClickListener) {

            itemView.name.text = match.contratante.nome

            if (type == 0){
                if (match.notaContratado != null){
                    itemView.btn_rating.visibility = View.VISIBLE
                }else{
                    itemView.btn_rating.visibility = View.GONE
                }
                if (match.notaContratante != null){
                    itemView.title_rating.text= "Avaliação:"
                    itemView.rating.visibility = View.VISIBLE
                    itemView.rating.text = match.notaContratante.toString()
                }else{
                    itemView.rating.visibility = View.GONE
                    itemView.title_rating.text= "Pendente"
                }

            }else{
                if (match.notaContratante != null){
                    itemView.btn_rating.visibility = View.VISIBLE
                }else{
                    itemView.btn_rating.visibility = View.GONE
                }
                if (match.notaContratado != null){
                    itemView.title_rating.text= "Avaliação:"
                    itemView.rating.visibility = View.VISIBLE
                    itemView.rating.text = match.notaContratado.toString()
                }else{
                    itemView.rating.visibility = View.GONE
                    itemView.title_rating.text= "Pendente"



                }
            }

            itemView.btn_rating.setOnClickListener { listener.onClickToRating(match.id) }


            itemView.type.text = match.tipoJogo
            itemView.gender.text = match.genero
            itemView.place.text = match.endereco.lagradouro
            itemView.date.text = match.data
            itemView.duration.text = match.comeca + " às " + match.termina

            if (match.tipoPessoa == "G") {
                if (match.aleatorio) {
                    itemView.img_card.setImageResource(R.drawable.ic_goleiro_laranja)

                } else {
                    itemView.img_card.setImageResource(R.drawable.ic_goleiro_dourada)
                }

            } else {

                if (match.aleatorio) {
                    itemView.img_card.setImageResource(R.drawable.ic_arbitro_laranja)
                } else {
                    itemView.img_card.setImageResource(R.drawable.ic_arbitro_dourado)

                }

            }


        }

    }

}

