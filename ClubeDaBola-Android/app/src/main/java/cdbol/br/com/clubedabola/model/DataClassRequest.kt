package cdbol.br.com.clubedabola.model


data class Hirer(
        var nome: String,
        var apelido: String,
        var imagem: String,
        var dataNascimento: String,
        var email: String,
        var senha: String)

data class DigitalWalletResquest(
        var contratante: String
)


data class Auth(
        val email: String,
        val senha: String)


data class MatchRequst(
        var customerId: String,
        var contratante: String,
        var preferenciaContratante: String?,
        var tipoJogo: String,
        var genero: String,
        var data: String,
        var endereco: AddressRequest,
        var comeca: String,
        var termina: String,
        var observacoes: String,
        var tipoPessoa: String,
        var aleatorio: Boolean,
        val valor: Int,
        val cvc: String)

data class AddressRequest(
        var lagradouro: String,
        var numero: String,
        var complemento: String,
        var bairro: String,
        var loc: LocRequest)

data class LocRequest(
        var coordinates: Array<Double>)


data class GoalKeeperRequest(
        val contratante: String,
        val genero: String,
        val tamanhoLuva: String,
        val tamanhoCamiseta: String,
        val dadosBancarios: BankInfoGoalKeeper,
        val endereco: AddressGoalKeeper,
        val telefone: String,
        val celular: String,
        val avatar: String,
        val notificacoes: Boolean,
        val raio: Int
)

data class BankInfoGoalKeeper(
        val cpf: String,
        val banco: String,
        val agencia: String,
        val conta: String
)

data class AddressGoalKeeper(
        val lagradouro: String,
        val numero: String,
        val complemento: String,
        val bairro: String,
        val loc: LocGoalKeeper
)

data class LocGoalKeeper(
        val coordinates: Array<Double>
)

data class DefaultDataClass(
        val message: String,
        val success: Boolean
)

data class PlayerUpdateRequest(
        val _id: String,
        val notificacoes: Boolean,
        val raio: Int
)


