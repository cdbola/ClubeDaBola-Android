package cdbol.br.com.clubedabola.screens.home.profile.payment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R


class PaymentAdapter(private val listener: (Int) -> Unit) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {

        return PaymentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(position, listener)
    }

    override fun getItemCount(): Int {
        return 1
    }

    class PaymentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(pos: Int,listener: (Int) -> Unit) {
            itemView.setOnClickListener{
                listener(pos)
            }
        }
    }
}
