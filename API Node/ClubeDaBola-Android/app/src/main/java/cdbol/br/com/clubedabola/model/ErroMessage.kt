package cdbol.br.com.clubedabola.model

import com.google.gson.annotations.SerializedName

data class ErroMessage(@SerializedName("Message") var erroData: ErroData)

