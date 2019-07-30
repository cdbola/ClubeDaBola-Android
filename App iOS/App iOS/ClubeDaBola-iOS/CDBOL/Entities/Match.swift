//
//  Match.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

public typealias RecentMatch = [Match]

public class HiredMatch: Codable {
    let partidas: [Match]
    let sucesso: Bool
    
    init(partidas: [Match], sucesso: Bool) {
        self.partidas = partidas
        self.sucesso = sucesso
    }
}

public class Match: Codable {
    let endereco: Endereco
    let matchID: String
    let aleatorio: Bool
    let comeca, contratante, data, genero: String
    let observacoes, termina, tipoJogo, tipoPessoa: String
    let valor: Int
    let created: String
    let matchV: Int?
    let tempo: String
    let id: String?
    let v: Int?
    let contratado:String?
   
    enum CodingKeys: String, CodingKey {
        case endereco
        case matchID = "id"
        case aleatorio, comeca, contratante, contratado ,data, genero, observacoes, termina, tipoJogo, tipoPessoa, valor
        case created = "_created"
        case matchV = "v"
        case tempo
        case id = "_id"
        case v = "_v"
    }
    
    init(endereco: Endereco, matchID: String, aleatorio: Bool, comeca: String, contratante: String, data: String, genero: String, observacoes: String, termina: String, tipoJogo: String, tipoPessoa: String, valor: Int, created: String, matchV: Int?, tempo: String, id: String?, v: Int?,contratado:String?) {
        self.endereco = endereco
        self.matchID = matchID
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
        self.matchV = matchV
        self.tempo = tempo
        self.id = id
        self.v = v
        self.contratado = contratado
    }
}

class Endereco: Codable {
    var loc: LOC
    var bairro, complemento, lagradouro, numero: String
    
    init(loc: LOC, bairro: String, complemento: String, lagradouro: String, numero: String) {
        self.loc = loc
        self.bairro = bairro
        self.complemento = complemento
        self.lagradouro = lagradouro
        self.numero = numero
    }
}

class LOC: Codable {
    var coordinates: [Double]
    
    init(coordinates: [Double]) {
        self.coordinates = coordinates
    }
}


public class NewMatch:Codable{
    let customerId:String
    let contratante:String
    let preferenciaContratante:String?
    let tipoJogo:String
    let genero:String
    let data:String
    let endereco:Endereco
    let comeca:String
    let termina:String
    let observacoes:String
    let tipoPessoa:String
    let aleatorio:Bool
    let valor:Int
    let cvc:String
    
    enum CodingKeys: String, CodingKey {
        case customerId
        case contratante, tipoJogo, genero, data, endereco, comeca, termina, observacoes, tipoPessoa, aleatorio, valor, cvc, preferenciaContratante
    }
    
    init(customerId: String, contratante: String, tipoJogo: String, genero: String,preferenciaContratante:String? ,data: String, endereco: Endereco, comeca: String, termina: String, observacoes: String, tipoPessoa: String, aleatorio: Bool, valor: Int, cvc: String) {
        self.customerId = customerId
        self.contratante = contratante
        self.tipoJogo = tipoJogo
        self.genero = genero
        self.data = data
        self.endereco = endereco
        self.comeca = comeca
        self.termina = termina
        self.observacoes = observacoes
        self.tipoPessoa = tipoPessoa
        self.aleatorio = aleatorio
        self.valor = valor
        self.cvc = cvc
        self.preferenciaContratante = preferenciaContratante
    }
    
}


public  class Rate: Codable {
    let partida: String
    let notaContratante, notaContratado: Double?
    
    init(partida: String, notaContratante: Double?, notaContratado: Double?) {
        self.partida = partida
        self.notaContratante = notaContratante
        self.notaContratado = notaContratado
    }
}
