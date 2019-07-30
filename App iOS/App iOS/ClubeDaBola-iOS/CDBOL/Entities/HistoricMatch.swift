//
//  HistoricMatch.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 21/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class HistoricMatch: Codable {
    let endereco: Endereco?
    let id: String?
    let aleatorio: Bool?
    let comeca: String?
    let contratante: ContrataPerson?
    let data, genero, observacoes, termina: String?
    let tipoJogo, tipoPessoa: String?
    let valor: Int?
    let created: String?
    let v: Int?
    let contratado: ContrataPerson?
    let notaContratante: Double?
    let tempo, historicMatchID: String?
    let notaContratado: Double?
    
    enum CodingKeys: String, CodingKey {
        case endereco
        case id = "_id"
        case aleatorio, comeca, contratante, data, genero, observacoes, termina, tipoJogo, tipoPessoa, valor
        case created = "_created"
        case v = "__v"
        case contratado, notaContratante, tempo
        case historicMatchID = "id"
        case notaContratado
    }
    
    init(endereco: Endereco?, id: String?, aleatorio: Bool?, comeca: String?, contratante: ContrataPerson?, data: String?, genero: String?, observacoes: String?, termina: String?, tipoJogo: String?, tipoPessoa: String?, valor: Int?, created: String?, v: Int?, contratado: ContrataPerson?, notaContratante: Double?, tempo: String?, historicMatchID: String?, notaContratado: Double?) {
        self.endereco = endereco
        self.id = id
        self.aleatorio = aleatorio
        self.comeca = comeca
        self.contratante = contratante
        self.data = data
        self.genero = genero
        self.observacoes = observacoes
        self.termina = termina
        self.tipoJogo = tipoJogo
        self.tipoPessoa = tipoPessoa
        self.valor = valor
        self.created = created
        self.v = v
        self.contratado = contratado
        self.notaContratante = notaContratante
        self.tempo = tempo
        self.historicMatchID = historicMatchID
        self.notaContratado = notaContratado
    }
}

class ContrataPerson: Codable {
    let id, nome, apelido, dataNascimento: String?
    let email, senha, created: String?
    let v: Int?
    let goleiro, customerID: String?
    let avatar: String?
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case nome, apelido, dataNascimento, email, senha
        case created = "_created"
        case v = "__v"
        case goleiro
        case customerID = "customerId"
        case avatar
    }
    
    init(id: String?, nome: String?, apelido: String?, dataNascimento: String?, email: String?, senha: String?, created: String?, v: Int?, goleiro: String?, customerID: String?, avatar: String?) {
        self.id = id
        self.nome = nome
        self.apelido = apelido
        self.dataNascimento = dataNascimento
        self.email = email
        self.senha = senha
        self.created = created
        self.v = v
        self.goleiro = goleiro
        self.customerID = customerID
        self.avatar = avatar
    }
}
