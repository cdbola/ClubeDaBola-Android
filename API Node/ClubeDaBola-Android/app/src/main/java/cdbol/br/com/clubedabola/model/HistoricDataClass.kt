package cdbol.br.com.clubedabola.model

data class HistoricMatch(
    val __v: Int,
    val _created: String,
    val _id: String,
    val aleatorio: Boolean,
    val comeca: String,
    val contratante: Contratante,
    val contratado: Contratante,
    val notaContratado :Double?,
    val notaContratante:Double?,
    val `data`: String,
    val endereco: Endereco,
    val genero: String,
    val id: String,
    val observacoes: String,
    val tempo: String,
    val termina: String,
    val tipoJogo: String,
    val tipoPessoa: String,
    val valor: Int
)

//data class Endereco(
//    val bairro: String,
//    val complemento: String,
//    val lagradouro: String,
//    val loc: Loc,
//    val numero: String
//)
//
//data class Loc(
//    val coordinates: List<Double>
//)

data class Contratante(
    val __v: Int,
    val _created: String,
    val _id: String,
    val apelido: String,
    val avatar: String,
    val customerId: String,
    val dataNascimento: String,
    val email: String,
    val goleiro: String,
    val nome: String,
    val senha: String
)