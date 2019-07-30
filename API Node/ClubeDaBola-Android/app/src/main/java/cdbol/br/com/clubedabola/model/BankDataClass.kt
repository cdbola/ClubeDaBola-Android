package cdbol.br.com.clubedabola.model

import com.squareup.moshi.Json

data class Bank (
        @field:Json(name= "Code") val Code: String,
        @field:Json(name= "CreatedAt") val CreatedAt: String,
        @field:Json(name= "DeletedAt") val DeletedAt: Any?,
        @field:Json(name= "IdBank") val IdBank: Int,
        @field:Json(name= "IsDeleted") val IsDeleted: Boolean,
        @field:Json(name= "Name") val Name: String,
        @field:Json(name= "UpdatedAt") val UpdatedAt: Any?

)