package cdbol.br.com.clubedabola.screens.escolha

import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import cdbol.br.com.clubedabola.view.ItemGoleiro

class EscolhaPresenter : BaseMvpPresenterImpl<EscolhaContact.View>(),
        EscolhaContact.Presenter {
    override fun tipoContratacao(descricao: String) {
    }

    var activity: EscolhaContact.View? = null

    override fun attachView(view: EscolhaContact.View) {
        super.attachView(view)
        activity = view
    }

    override fun setEdtEscolha(edt_tipo_solicitacao: ItemGoleiro) {
        itemViewEscolha = edt_tipo_solicitacao
    }

    var itemViewEscolha: ItemGoleiro? = null
}