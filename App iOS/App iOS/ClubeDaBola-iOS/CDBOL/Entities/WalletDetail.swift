//
//  WalletDetail.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class WalletDetail: Codable {
    let sucesso: Bool?
    let valorPendente, valorTotal, valorDisponivel: Int?
    let extrato: [Extrato]?
    let mensagem: String?
    
    init(sucesso: Bool?, valorPendente: Int?, valorTotal: Int?, valorDisponivel: Int?, extrato: [Extrato]?, mensagem: String?) {
        self.sucesso = sucesso
        self.valorPendente = valorPendente
        self.valorTotal = valorTotal
        self.valorDisponivel = valorDisponivel
        self.extrato = extrato
        self.mensagem = mensagem
    }
}

public class Extrato: Codable {
    let retirado: Bool?
    let id, dataAdicao: String?
    let valor: Int?
    let idContratado: String?
    
    enum CodingKeys: String, CodingKey {
        case retirado
        case id = "_id"
        case dataAdicao, valor, idContratado
    }
    
    init(retirado: Bool?, id: String?, dataAdicao: String?, valor: Int?, idContratado: String?) {
        self.retirado = retirado
        self.id = id
        self.dataAdicao = dataAdicao
        self.valor = valor
        self.idContratado = idContratado
    }
}
