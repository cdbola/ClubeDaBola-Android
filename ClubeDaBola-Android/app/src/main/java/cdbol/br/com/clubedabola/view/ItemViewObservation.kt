package cdbol.br.com.clubedabola.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import cdbol.br.com.clubedabola.R

class ItemViewObservation @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {


    init {
        LayoutInflater.from(context).inflate(R.layout.item_observacao_partida, this, true)
    }


}