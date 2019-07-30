package cdbol.br.com.clubedabola.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdbol.br.com.clubedabola.R
import kotlinx.android.synthetic.main.fragment_blog.*

class BlogFragment  :  FragmentAbstrato() {

    companion object {
        @JvmField
        val TITULO = "Blog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_blog, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        web_view.loadUrl("https://blog.cdbola.com.br")
    }


    }