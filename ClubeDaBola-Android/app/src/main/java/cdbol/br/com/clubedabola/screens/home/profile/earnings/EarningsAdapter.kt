package cdbol.br.com.clubedabola.screens.home.profile.earnings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.Extrato
import cdbol.br.com.clubedabola.utils.DateObjUtils
import cdbol.br.com.clubedabola.utils.MaskUtils
import cdbol.br.com.clubedabola.utils.ViewUtils
import kotlinx.android.synthetic.main.item_extract.view.*


class EarningsAdapter(private var extract: List<Extrato>) : RecyclerView.Adapter<EarningsAdapter.EarningsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningsViewHolder {

        return EarningsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_extract, parent, false))
    }

    override fun onBindViewHolder(holder: EarningsViewHolder, position: Int) {

        holder.bindView(extract!![position])
    }

    override fun getItemCount(): Int {
        return extract.size
    }

    class EarningsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindView(extract: Extrato) {

            itemView.tv_date.text = DateObjUtils.formatDate(extract.dataAdicao)
            itemView.tv_amount.text = ViewUtils.formatValueCurrency(extract.valor.toDouble())

        }

    }

}
