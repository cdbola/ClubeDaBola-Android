package cdbol.br.com.clubedabola.model

import com.google.gson.annotations.SerializedName

data class Login(

        @SerializedName("Id")
        val Id: Int,
        @SerializedName("NomeCompleto")
        val NomeCompleto: String,
        @SerializedName("DescricaoTipoCadastro")
        val DescricaoTipoCadastro: String,
        @SerializedName("email")
        val Email: String,
        @SerializedName("Apelido")
        val Apelido: String)
