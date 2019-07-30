package cdbol.br.com.clubedabola.api

import cdbol.br.com.clubedabola.model.*
import okhttp3.MultipartBody
import retrofit2.http.*
import rx.Observable
import retrofit2.http.POST
import retrofit2.http.Multipart



interface ClubeDaBolaService {

    @POST(ApiSettings.SIGNUP)
    fun createHirer(@Body hirer: Hirer): Observable<Hirer>

    @POST(ApiSettings.LOGIN)
    fun login(@Body auth: Auth): Observable<HirerResponse>

    @POST(ApiSettings.FACELOGIN)
    fun postFaceLogin(@Body facebookLogin: FacebookLogin): Observable<HirerResponse>

    @POST(ApiSettings.LOGOUT)
    fun logout(@Header("Authorization") authHeader: String ) : Observable<TokenUser>

    @POST(ApiSettings.MATCH)
    fun createMatch(@Body match: MatchRequst) : Observable<Match>

    @PUT(ApiSettings.MATCHTORATE)
    fun putRatingMatch(@Body ratingMatch: RatingMatch) : Observable<MatchRatingResponse>

    @POST(ApiSettings.GOALKEEPER)
    fun becomePlayer(@Body goalKeeper: GoalKeeperRequest) : Observable<Goalkeeper>

    @GET(ApiSettings.GOALKEEPER)
    fun getGoalkeeper() : Observable<List<Goalkeeper2>>

    @GET(ApiSettings.GOALKEEPERNICKNAME)
    fun getGoalkeeperByNickname(@Path ("apelido")nickName: String?): Observable<List<ChooseDataClass>>

    @POST(ApiSettings.REFEREE)
    fun becomeReferee(@Body goalKeeper: GoalKeeperRequest) : Observable<Goalkeeper>

    @POST(ApiSettings.DIGITALWALLET)
    fun postDigitalWallet(@Body digitalWallet: DigitalWalletResquest) : Observable<DigitalWallet>

    @GET(ApiSettings.HIRERID)
    fun getHirerById(@Path("Id") hirerId : String) : Observable<HirerId>

    @POST(ApiSettings.CREDITCARD)
    fun postCreditCard(@Body creditCardRequest: CreditCardRequest) : Observable<Customer>

    @POST(ApiSettings.CUSTOMER)
    fun postCustomer(@Body customerRequest: CustomerRequest) : Observable<Customer>

    @GET(ApiSettings.GOALKEEPERID)
    fun deleteGoalKeeperById(@Path("Id") goalKeeperId : String) : Observable<Goalkeeper>

    @GET(ApiSettings.RECENTMATCHS)
    fun getRecentMatch(@Path("Id") id : String) : Observable<List<RecentMatch>>

    @GET(ApiSettings.RECENTHIRERMATCHS)
    fun getRecentHirerMatch(@Path("Id") id : String) : Observable<RecentHirerMatch>

    @GET(ApiSettings.PASSEDMATCHS)
    fun getPassedMatch(@Path("Id") id : String) : Observable<List<HistoricMatch>>

    @GET(ApiSettings.PASSEDHIRERMATCHS)
    fun getPassedHirerMatch(@Path("Id") id : String) : Observable<List<HistoricMatch>>

    @POST(ApiSettings.POSTATTACHMATCH)
    fun postAttachMatch(@Body attachMatchRequest: AttachMatchRequest) : Observable<AttachMatchRequest>

    @DELETE(ApiSettings.DETACHMATCH)
    fun detachMatch(@Path("matchId") matchId : String) : Observable<RecentHirerMatch>

    @DELETE(ApiSettings.DELETEMATCH)
    fun deleteMatch(@Path("matchId") matchId : String) : Observable<RecentHirerMatch>

    @GET(ApiSettings.RANCKINGOLEIRO)
    fun getRankingGoalKeeper() : Observable<List<Hired>>

    @GET(ApiSettings.RANCKINGARBITRO)
    fun getRankingReferee() : Observable<List<Hired>>

    @GET(ApiSettings.WALLETDETAIL)
    fun getWalletDetail(@Path("Id") id : String) : Observable<WalletDetail>

    @POST(ApiSettings.WITHDRAW)
    fun postWithDraw(@Body withDrawRequest: WithDrawRequest) : Observable<DefaultDataClass>

    @POST(ApiSettings.PUSH)
    fun postNotification(@Body push: Push) : Observable<DefaultDataClass>

    @Multipart
    @PATCH(ApiSettings.AVATAR)
    fun upload(@Part filePart: MultipartBody.Part, @Path("Id") goalKeeperId : String) : Observable<Goalkeeper>

    @POST(ApiSettings.GOALKEEPER)
    fun updatePlayer(@Body goalKeeper: PlayerUpdateRequest) : Observable<Goalkeeper>

    @POST(ApiSettings.REFEREE)
    fun updateReferee(@Body goalKeeper: PlayerUpdateRequest) : Observable<Goalkeeper>

}