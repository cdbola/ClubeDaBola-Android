package cdbol.br.com.clubedabola.screens.home.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.RecentMatch
import kotlinx.android.synthetic.main.item_home_hired.view.*
import kotlinx.android.synthetic.main.item_home_i_hired.view.*
import kotlinx.android.synthetic.main.item_home_w_hired.view.*


class HomeAdapter(private val listener: ClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    var typeHired:Int = 0

    private var matchList: List<RecentMatch>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_hired, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val match = matchList!![position]
        holder.bindView(match, typeHired, listener)

    }

    override fun getItemCount(): Int {
        return if (matchList != null) matchList!!.size else 0
    }

    fun notifyAdapter(matchList: List<RecentMatch>?, type: Int){
        this.typeHired = type
        this.matchList = matchList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClickCancel(position: String)
        fun onClickAttach(position: String, contratante: String)
        fun onClickDetach(position: String)
    }


    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(match: RecentMatch, type: Int, listener: ClickListener) {

            itemView.btn_cancel.setOnClickListener { listener.onClickCancel(match.id) }
            itemView.btn_attach.setOnClickListener { listener.onClickAttach(match.id, match.contratante) }
            itemView.btn_detach.setOnClickListener { listener.onClickDetach(match.id) }




            itemView.type.text = match.tipoJogo
            itemView.gender.text = match.genero
            itemView.place.text = match.endereco.lagradouro
            itemView.date.text = match.data
            itemView.duration.text = match.comeca + " às " + match.termina
            itemView.observation.text = match.observacoes

            if (type == 0){
                itemView.include_i_hired.visibility = View.VISIBLE
                itemView.include_w_hired.visibility = View.GONE
            }else{

                if(match.contratado != null) {
                    itemView.btn_detach.visibility = View.VISIBLE
                    itemView.btn_attach.visibility = View.GONE
                } else {
                    itemView.btn_detach.visibility = View.GONE
                    itemView.btn_attach.visibility = View.VISIBLE
                }

                itemView.include_i_hired.visibility = View.GONE
                itemView.include_w_hired.visibility = View.VISIBLE

            }


            if(match.contratado == null){
                itemView.status.text = "Pendente"
            }else{
                itemView.status.text = "Aceito"

            }

            var title :String
            if (match.tipoPessoa == "G") {
               title = "Goleiro"
                if (match.aleatorio){
                    itemView.img_card.setImageResource(R.drawable.ic_goleiro_laranja)

                }else{
                    itemView.img_card.setImageResource(R.drawable.ic_goleiro_dourada)
                }

            } else {
                title = "Árbitro"

                if (match.aleatorio){
                    itemView.img_card.setImageResource(R.drawable.ic_arbitro_laranja)
                }else{
                    itemView.img_card.setImageResource(R.drawable.ic_arbitro_dourado)

                }

            }

            if (match.aleatorio) title += " Aleatório"
            itemView.title.text = title

        }

    }

}
