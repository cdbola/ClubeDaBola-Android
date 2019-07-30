package cdbol.br.com.clubedabola.model

import com.squareup.moshi.Json

data class Customer(
        @field:Json(name = "customer") val customer: CustomerCard,
        @field:Json(name = "mensagem") val message: String,
        @field:Json(name = "sucesso") val success: Boolean
)

data class CustomerCard(
        @field:Json(name = "card") val card: Card,
        @field:Json(name = "creditCard") val creditCard: CustomerCreditCard,
        @field:Json(name = "isPresential") val isPresential: Boolean,
        @field:Json(name = "method") val method: String
)

data class Card(
        @field:Json(name = "brand") val brand: String,
        @field:Json(name = "store") val store: Boolean
)

data class CustomerCreditCard(
        @field:Json(name = "brand") val brand: String,
        @field:Json(name = "first6") val first6: String,
        @field:Json(name = "id") val id: String,
        @field:Json(name = "last4") val last4: String,
        @field:Json(name = "store") val store: Boolean
)

