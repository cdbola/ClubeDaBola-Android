//
//  RankingPerson.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class RankingPerson: Codable {
    let arbitros, goleiros: [Arbitro]?
    
    init(arbitros: [Arbitro]?, goleiros: [Arbitro]?) {
        self.arbitros = arbitros
        self.goleiros = goleiros
    }
}

class Arbitro: Codable {
    let nome: String?
    let avatar: String?
    let media: Double?
    let partidas: Int?
    let endereco: Endereco?
    
    init(nome: String?, avatar: String?, media: Double?, partidas: Int?, endereco: Endereco?) {
        self.nome = nome
        self.avatar = avatar
        self.media = media
        self.partidas = partidas
        self.endereco = endereco
    }
}



