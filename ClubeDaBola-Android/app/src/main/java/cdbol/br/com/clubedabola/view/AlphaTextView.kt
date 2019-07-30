package cdbol.br.com.clubedabola.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView


class AlphaTextView : TextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onSetAlpha(alpha: Int): Boolean {
        setTextColor(getTextColors().withAlpha(alpha))
        setHintTextColor(getHintTextColors().withAlpha(alpha))
        setLinkTextColor(getLinkTextColors().withAlpha(alpha))
        return true
    }

    fun setColor() {
        setTextColor(getTextColors().defaultColor)
        setHintTextColor(getTextColors().defaultColor)
        setLinkTextColor(getTextColors().defaultColor)
    }
}