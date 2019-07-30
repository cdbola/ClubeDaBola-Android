//
//  HIrer.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class Hirer: Codable {
    let id, nome, apelido, dataNascimento: String?
    
    init(id: String?, nome: String?, apelido: String?, dataNascimento: String?) {
        self.id = id
        self.nome = nome
        self.apelido = apelido
        self.dataNascimento = dataNascimento
    }
}
