package cdbol.br.com.clubedabola.model

import com.squareup.moshi.Json

data class HirerId(
        @field:Json(name= "apelido") var nickname: String,
        @field:Json(name= "dataNascimento") var birthday: String,
        @field:Json(name= "_id")  var id: String,
        @field:Json(name= "email") var email: String,
        @field:Json(name= "nome") var name: String,
        @field:Json(name= "avatar") var avatar: String,
        @field:Json(name= "goleiro") var goleiro: Goalkeeper,
        @field:Json(name= "arbitro") var arbitro: Goalkeeper,
        @field:Json(name= "customerId") var customerId: String

)

data class HirerIdObj(

        var nickname: String = "",
        var birthday: String = "",
        var id: String = "",
        var email: String = "",
        var name: String = "",
        var avatar:String = "avatar",
        var goleiro: Goalkeeper? = null,
        var arbitro: Goalkeeper? = null,
        var customerId: String = ""
)