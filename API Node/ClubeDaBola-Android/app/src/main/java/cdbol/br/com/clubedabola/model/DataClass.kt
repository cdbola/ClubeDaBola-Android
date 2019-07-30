package cdbol.br.com.clubedabola.model
import com.squareup.moshi.Json
import java.io.Serializable


data class HirerResponse(
        @field:Json(name = "contratante") var contratante: HirerObj,
        @field:Json(name = "token") var token : String,
        @field:Json(name = "success") var success : Boolean,
        @field:Json(name = "message") var message : String)

data class HirerObj(
        @field:Json(name = "_id") val _id: String,
        @field:Json(name = "nome") val nome: String,
        @field:Json(name = "apelido") val apelido: String,
        @field:Json(name = "dataNascimento") val dataNascimento: String,
        @field:Json(name = "email") val email: String,
        @field:Json(name = "_created") val _created: String)

data class DigitalWallet(
        @field:Json(name = "contratante") var hirerId: String
)


data class Match(
        @field:Json(name = "contratante") var hirerId: String,
        @field:Json(name = "contratado") var playerId: String,
        @field:Json(name = "tipoJogo") var matchType: String,
        @field:Json(name = "genero") var gender: String,
        @field:Json(name = "data") var date: String,
        @field:Json(name = "endereco") var address: Address,
        @field:Json(name = "comeca") var start: String,
        @field:Json(name = "termina") var end: String,
        @field:Json(name = "observacoes") var obs: String,
        @field:Json(name = "tipoPessoa") var personType: String,
        @field:Json(name = "aleatorio") var random: Boolean,
        @field:Json(name = "valor") var value: Double)

data class Address(
        @field:Json(name = "lagradouro") var logradouro: String,
        @field:Json(name = "numero") var number: String,
        @field:Json(name = "complemento") var complement: String,
        @field:Json(name = "bairro") var neighbor: String,
        @field:Json(name = "loc") var loc: Loc)

data class Loc(
        @field:Json(name = "coordinates") var coordinates: Array<Double>)


data class Goalkeeper(
        @field:Json(name = "_id") var _id: String,
        @field:Json(name = "contratante") var hirerId: String,
        @field:Json(name = "genero") var gender: String,
        @field:Json(name = "tamanhoLuva") var gloveSize: String,
        @field:Json(name = "tamanhoCamiseta") var tshirtSize: String,
        @field:Json(name = "dadosBancarios") var bankInfo: KeeperBank,
        @field:Json(name = "endereco") var endereco: KeeperAddress,
        @field:Json(name = "telefone") var phone: String,
        @field:Json(name = "celular") var cell: String,
        @field:Json(name = "avatar") var avatar: String,
        @field:Json(name = "notificacoes") var notificacoes: Boolean,
        @field:Json(name = "raio") var coord: Double
)


data class Goalkeeper2(
        @field:Json(name = "contratante") var hirer: Contratante,
        @field:Json(name = "genero") var gender: String,
        @field:Json(name = "tamanhoLuva") var gloveSize: String,
        @field:Json(name = "tamanhoCamiseta") var tshirtSize: String,
        @field:Json(name = "dadosBancarios") var bankInfo: KeeperBank,
        @field:Json(name = "endereco") var endereco: KeeperAddress,
        @field:Json(name = "telefone") var phone: String,
        @field:Json(name = "celular") var cell: String,
        @field:Json(name = "avatar") var avatar: String,
        @field:Json(name = "notificacoes") var notificacoes: Boolean,
        @field:Json(name = "raio") var coord: Double
)

data class KeeperBank(
        @field:Json(name = "cpf") var cpf: String,
        @field:Json(name = "banco") var bank: String,
        @field:Json(name = "agencia") var branch: String,
        @field:Json(name = "conta") var account: String
)

data class KeeperAddress(
        @field:Json(name = "lagradouro") var lagradouro: String,
        @field:Json(name = "numero") var number: String,
        @field:Json(name = "complemento") var complement: String,
        @field:Json(name = "bairro") var neighbor: String,
        @field:Json(name = "loc") var loc: KeeperLoc
)

data class KeeperLoc(
        @field:Json(name = "coordinates") var coordinates: List<Double>
)

data class RatingMatch(
    val notaContratado: Double,
    val partida: String
)

data class MatchRatingResponse(
    val mensagem: String,
    val partida: Partida,
    val sucesso: Boolean
)

data class ChooseDataClass(
    val __v: Int,
    val _created: String,
    val _id: String,
    val apelido: String,
    val avatar: String,
    val customerId: String,
    val dataNascimento: String,
    val email: String,
    val goleiro: Goleiro,
    val nome: String,
    val senha: String
) :Serializable

data class Goleiro(
    val __v: Int,
    val _created: String,
    val _id: String,
    val avatar: String,
    val celular: String,
    val contratante: String,
    val dadosBancarios: DadosBancarios,
    val endereco: Endereco,
    val genero: String,
    val notificacoes: Boolean,
    val raio: Int,
    val tamanhoCamiseta: String,
    val tamanhoLuva: String,
    val telefone: String
) : Serializable

data class DadosBancarios(
    val agencia: String,
    val banco: String,
    val conta: String,
    val cpf: String
) : Serializable

data class ItemGoalkeeper(
        val _id: String,
        val nome: String,
        val avatar: String
):Serializable



