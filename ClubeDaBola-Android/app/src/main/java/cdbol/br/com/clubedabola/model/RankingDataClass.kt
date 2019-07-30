package cdbol.br.com.clubedabola.model

data class Hired(
        val endereco: Endereco,
        val media: Int,
        val nome: String,
        val partidas: Int,
        var orderNum: Int = 0
)
