package cdbol.br.com.clubedabola.screens.escolha

import cdbol.br.com.clubedabola.mvp.BaseMvpPresenter
import cdbol.br.com.clubedabola.mvp.BaseMvpView
import cdbol.br.com.clubedabola.view.ItemGoleiro

interface EscolhaContact {

    interface View : BaseMvpView {
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun setEdtEscolha(edt_tipo_solicitacao: ItemGoleiro)
        fun tipoContratacao(descricao: String)
    }
}