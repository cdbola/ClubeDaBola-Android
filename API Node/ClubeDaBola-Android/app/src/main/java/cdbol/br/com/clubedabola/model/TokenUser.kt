package cdbol.br.com.clubedabola.model

import com.squareup.moshi.Json

data class TokenUser(
        @field:Json(name = "access_token")
        val token: String,
        @field:Json(name = "token_type")
        val tokenType: String,
        @field:Json(name = "expires_in")
        val expiresIn: String

)
