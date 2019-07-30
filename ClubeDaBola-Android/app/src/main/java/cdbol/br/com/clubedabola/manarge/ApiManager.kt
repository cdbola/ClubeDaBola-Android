package cdbol.br.com.clubedabola.manarge

import cdbol.br.com.clubedabola.api.ClubeDaBolaService
import cdbol.br.com.clubedabola.model.*
import cdbol.br.com.clubedabola.utils.PreferencesString
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object ApiManager {

   // private const val SERVER: String = "https://clubedabola.azurewebsites.net/api/"
private const val SERVER: String = "https://cdbola.herokuapp.com/api/v1/"


    private lateinit var mClubeDaBolaService: ClubeDaBolaService

    init {
        val retrofit = initRetrofit()
        initServices(retrofit)
    }

    private fun initRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .addHeader("Authorization", "${PreferencesString.instance.getString("token")}")
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }

        return Retrofit.Builder().baseUrl(SERVER)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(createMoshiConverter())
                .client(client.build())
                .build()
    }

    private fun createMoshiConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

    private fun initServices(retrofit: Retrofit) {
        mClubeDaBolaService = retrofit.create(ClubeDaBolaService::class.java)
    }

    fun createHirer(hirer : Hirer) =
            mClubeDaBolaService.createHirer(hirer)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!


    fun tokenRegister(auth: Auth) =
            mClubeDaBolaService.login(auth)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun postFaceLogin(facebookLogin: FacebookLogin) =
            mClubeDaBolaService.postFaceLogin(facebookLogin)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!


    fun logout(token : String) =
            mClubeDaBolaService.logout(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun createMatch(match: MatchRequst) =
        mClubeDaBolaService.createMatch(match)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

    fun becomePlayer(goalKeeper: GoalKeeperRequest) =
            mClubeDaBolaService.becomePlayer(goalKeeper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun becomeReferee(goalKeeper: GoalKeeperRequest) =
            mClubeDaBolaService.becomeReferee(goalKeeper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun getGoalkeeper() = mClubeDaBolaService.getGoalkeeper()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getGoalkeeperByNickname(nickName: String?) = mClubeDaBolaService.getGoalkeeperByNickname(nickName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!




    fun getHirerById(hirerId: String) = mClubeDaBolaService.getHirerById(hirerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun uploadImage(filePart: MultipartBody.Part, hirerId: String) = mClubeDaBolaService.upload(filePart,hirerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun postDigitalWallet(digitalWallet: DigitalWalletResquest) = mClubeDaBolaService.postDigitalWallet(digitalWallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun postCreditCard(creditCardRequest: CreditCardRequest) = mClubeDaBolaService.postCreditCard(creditCardRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun postCustomer(customerRequest: CustomerRequest) = mClubeDaBolaService.postCustomer(customerRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getRecentMatch(id: String) = mClubeDaBolaService.getRecentMatch(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getRecentHirerMatch(id: String) = mClubeDaBolaService.getRecentHirerMatch(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getPassedMatch(id: String) = mClubeDaBolaService.getPassedMatch(id)
            .subscribeOn(Schedulers.io())
            .doOnError {  }
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getPassedHirerMatch(id: String) = mClubeDaBolaService.getPassedHirerMatch(id)
            .subscribeOn(Schedulers.io())
            .doOnError {  }
            .observeOn(AndroidSchedulers.mainThread())!!

    fun postAttachMatch(attachMatchRequest: AttachMatchRequest) = mClubeDaBolaService.postAttachMatch(attachMatchRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun detachMatch(id: String) = mClubeDaBolaService.detachMatch(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun deleteMatch(id: String) = mClubeDaBolaService.deleteMatch(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getRankingGoalkeeper() = mClubeDaBolaService.getRankingGoalKeeper()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun getRankingReferee() = mClubeDaBolaService.getRankingReferee()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!


    fun getWalletDetails(id: String) = mClubeDaBolaService.getWalletDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun postWithDraw(withDrawRequest: WithDrawRequest) = mClubeDaBolaService.postWithDraw(withDrawRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun putRatingMatch(ratingMatch: RatingMatch) =
            mClubeDaBolaService.putRatingMatch(ratingMatch)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun postNotification(push: Push) = mClubeDaBolaService.postNotification(push)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun deleteGoalKeeperById(goalKeeperId: String) = mClubeDaBolaService.deleteGoalKeeperById(goalKeeperId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!


    fun updatePlayer(player: PlayerUpdateRequest) =
            mClubeDaBolaService.updatePlayer(player)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun updateReferee(player: PlayerUpdateRequest) =
            mClubeDaBolaService.updateReferee(player)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

}