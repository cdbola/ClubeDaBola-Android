package cdbol.br.com.clubedabola.screens.escolha

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.model.ChooseDataClass
import cdbol.br.com.clubedabola.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hired_type.view.*


class ChooseAdapter(private val listener:ClickListener) : RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder>() {


    private var goalkeepers: List<ChooseDataClass>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseViewHolder {

        return ChooseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hired_type, parent, false))
    }


    override fun onBindViewHolder(holder: ChooseViewHolder, position: Int) {
        val goalkeeper = goalkeepers!![position]
        holder.bind(goalkeeper, listener)

    }

    override fun getItemCount(): Int {
        return if (goalkeepers != null) goalkeepers!!.size else 0
    }

    fun notifyAdapter(goalkeepers: List<ChooseDataClass>?) {
        this.goalkeepers = goalkeepers
        notifyDataSetChanged()
    }
    interface ClickListener {
        fun onClickCancel(goalkeeper: ChooseDataClass)

    }

    class ChooseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(goalkeeper: ChooseDataClass, listener: ClickListener) {

            Picasso.get().load(goalkeeper.avatar).placeholder(R.drawable.ic_profile).transform(CircleTransform()).into( itemView.img_profile)
            itemView.tv_name.text = goalkeeper.nome

            itemView.setOnClickListener { listener.onClickCancel(goalkeeper) }

        }

    }


}
