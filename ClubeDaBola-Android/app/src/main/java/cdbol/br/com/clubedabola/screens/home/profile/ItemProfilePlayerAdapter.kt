package cdbol.br.com.clubedabola.screens.home.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import kotlinx.android.synthetic.main.item_profile.view.*


class ItemProfilePlayerAdapter(private var items: ArrayList<ItemProfile>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_OTHERS = 1
        const val TYPE_BECOME = 4
        const val TYPE_BANNER = 7

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            TYPE_BECOME -> TYPE_BECOME
            TYPE_BANNER -> TYPE_BANNER
            else -> TYPE_OTHERS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var layout:Int? = 0

        when(getItemViewType(viewType)){
            TYPE_BECOME -> return ItemProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_become_player, parent, false))
            TYPE_OTHERS -> return ItemProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_others, parent, false))
            TYPE_BANNER ->  return BannerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_banner, parent, false))
        }


        return ItemProfileViewHolder(LayoutInflater.from(parent.context).inflate(layout!!, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position) {

            TYPE_BECOME -> {

            }

        }
        if(getItemViewType(position) != TYPE_BANNER ){
            (holder as ItemProfileViewHolder).let {
                it.desc.text = items[position].title
                it.amount.text = items[position].desc
            }

            holder.bindListener(position, listener)
        }else{
            (holder as BannerViewHolder).bindListener(position, listener)
        }



    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    fun notifyAdapter(items: ArrayList<ItemProfile>){
        this.items = items
        notifyDataSetChanged()
    }

    class ItemProfileViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val desc = itemView!!.tv_desc!!
        val amount = itemView!!.tv_amount!!

        fun bindListener(pos: Int,listener: (Int) -> Unit) {
            itemView.setOnClickListener{
                listener(pos)
            }

        }

    }

    class BannerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        fun bindListener(pos: Int,listener: (Int) -> Unit) {

            itemView.setOnClickListener{
                listener(pos)
            }


        }
    }


}
