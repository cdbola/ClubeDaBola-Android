package cdbol.br.com.clubedabola.model

data class FacebookLogin(
        val nome: String,
        val apelido: String,
        val imagem: String,
        val dataNascimento: String,
        val email: String

)

data class Push(
    val contratante: String,
    val tokenNotification: String
)