package cdbol.br.com.clubedabola.screens.home

import android.util.Log
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.model.Push
import cdbol.br.com.clubedabola.mvp.BaseMvpPresenterImpl
import com.google.firebase.iid.FirebaseInstanceId

class HomePresenter : BaseMvpPresenterImpl<HomeContract.View>(),
        HomeContract.Presenter {




    fun postNotification() {

        val token = FirebaseInstanceId.getInstance().token
        Log.d("Token da App", token)

        var pushRequest = Push(
                mView!!.getHirer().id,
                token.toString()
        )

        ApiManager.postNotification(pushRequest)
                .subscribe({

                }, {

                    Log.d("Error create push", it.message)
                })

    }

}
