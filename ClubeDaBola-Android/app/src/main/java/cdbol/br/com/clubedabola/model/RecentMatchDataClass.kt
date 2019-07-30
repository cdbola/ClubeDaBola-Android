package cdbol.br.com.clubedabola.model

import java.io.Serializable

data class RecentMatch(
    val __v: Int,
    val _created: String,
    val _id: String,
    val aleatorio: Boolean,
    val comeca: String,
    val contratado: String,
    val contratante: String,
    val `data`: String,
    val endereco: Endereco,
    val genero: String,
    val id: String,
    val notaContratado: Int,
    val notaContratante: Int,
    val observacoes: String,
    val tempo: String,
    val termina: String,
    val tipoJogo: String,
    val tipoPessoa: String,
    val valor: Int
)

data class PassedMatch(
        val __v: Int,
        val _created: String,
        val _id: String,
        val aleatorio: Boolean,
        val comeca: String,
        val contratado: HirerId,
        val contratante: HirerId,
        val `data`: String,
        val endereco: Endereco,
        val genero: String,
        val id: String,
        val notaContratado: Int,
        val notaContratante: Int,
        val observacoes: String,
        val tempo: String,
        val termina: String,
        val tipoJogo: String,
        val tipoPessoa: String,
        val valor: Int
)

data class Endereco(
    val bairro: String,
    val complemento: String,
    val lagradouro: String,
    val loc: LocRecentMatch,
    val numero: String
) : Serializable

data class LocRecentMatch(
    val coordinates: List<Double>
) : Serializable


data class RecentHirerMatch(
    val partidas: List<RecentMatch>,
    val pendentes: List<Any>,
    val sucesso: Boolean
)

data class PassedHirerMatch(
        val partidas: List<PassedMatch>,
        val pendentes: List<Any>,
        val sucesso: Boolean
)

data class Partida(
    val __v: Int,
    val _created: String,
    val _id: String,
    val aleatorio: Boolean,
    val comeca: String,
    val contratado: String,
    val contratante: String,
    val `data`: String,
    val endereco: Endereco,
    val genero: String,
    val id: String,
    val notaContratado: Int,
    val notaContratante: Int,
    val observacoes: String,
    val tempo: String,
    val termina: String,
    val tipoJogo: String,
    val tipoPessoa: String,
    val valor: Int
)


data class AttachMatchRequest(
    val contratante: String,
    val partida: String
)