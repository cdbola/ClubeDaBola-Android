package cdbol.br.com.clubedabola.api

object ApiSettings {

    const val SIGNUP = "contratantes/auth/signup"
    const val LOGIN = "contratantes/auth/login"
    const val LOGOUT = "contatantes/auth/logout"

    const val FACELOGIN = "contratantes/auth/login/facebook"

    const val HIRERID = "contratantes/{Id}"

    const val MATCH = "partidas"

    const val MATCHTORATE = "partidas/avaliar"

    const val GOALKEEPER = "goleiros"
    const val GOALKEEPERID = "goleiros/{Id}"

    const val GOALKEEPERNICKNAME = "goleiros/buscar/{apelido}"

    const val REFEREE = "arbitros"
    const val REFEREEID = "arbitros/{Id}"

    const val AVATAR = "contratantes/{Id}/atualizarAvatar"

    const val DIGITALWALLET = "carteiras"

    const val CREDITCARD = "creditCards"

    const val CUSTOMER = "customers"

    const val RECENTMATCHS = "partidas/recentes/{Id}"

    const val RECENTHIRERMATCHS = "partidas/recentes/contratado/{Id}"

    const val PASSEDMATCHS = "partidas/passadas/{Id}"

    const val PASSEDHIRERMATCHS = "partidas/passadas/contratado/{Id}"

    const val POSTATTACHMATCH = "partidas/vincular"

    const val DETACHMATCH = "partidas/{matchId}/desvincular"

    const val DELETEMATCH = "partidas/{matchId}"

    const val RANCKINGARBITRO = "ranking/arbitros"
    const val RANCKINGOLEIRO = "ranking/goleiros"

    const val WALLETDETAIL = "carteira/detail/{Id}"
    const val WITHDRAW = "resgatar/carteira"


    const val PUSH = "notificacoes"







}