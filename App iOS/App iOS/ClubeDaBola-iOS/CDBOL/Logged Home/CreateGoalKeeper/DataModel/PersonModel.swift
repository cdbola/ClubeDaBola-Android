//
//  PersonModel.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class PersonModel {
    var genero, tamanhoLuva, tamanhoCamiseta, telefone: String
    var type: TypePerson?
    var dadosBancarios: DadosBancarios?
    let endereco: Endereco?
    var account, bank, agency, cpf: String
    
    init() {
        self.genero = ""
        self.tamanhoLuva = ""
        self.tamanhoCamiseta = ""
        self.endereco = Endereco(loc: LOC(coordinates: []), bairro: "", complemento: "", lagradouro: "", numero: "")
        self.telefone = ""
        self.bank = ""
        self.agency = ""
        self.account = ""
        self.cpf = ""
    }
    
    func setType(typeString:String) {
        if typeString == "GOALKEEPER" {
            self.type = .GoalKeeper
        } else {
            self.type = .Referee
        }
    }
    
    func getType() -> String {
        if self.type == .GoalKeeper  {
            return  "GOALKEEPER"
        } else {
            return "Referee"
        }
    }

}


