package cdbol.br.com.clubedabola.model

import java.io.Serializable

data class WalletDetail(
    val extrato: List<Extrato>,
    val mensagem: String,
    val sucesso: Boolean,
    val valorDisponivel: Int,
    val valorPendente: Int,
    val valorTotal: Int
) : Serializable

data class Extrato(
    val _id: String,
    val dataAdicao: String,
    val idContratado: String,
    val retirado: Boolean,
    val valor: Int
): Serializable


data class WithDrawRequest(
    val contratante: String
)