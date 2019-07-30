//
//  GoalKeeper.swift
//  CDBOL
//
//  Created by Paulo Rosa on 17/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class GoalKeeper: Codable {
    let _id: String?
    let contratante, genero, tamanhoLuva, tamanhoCamiseta: String
    let dadosBancarios: DadosBancarios
    let endereco: Endereco
    let telefone, celular, avatar: String
    let notificacoes: Bool
    let raio: Int

    init(_id: String?, contratante: String, genero: String, tamanhoLuva: String, tamanhoCamiseta: String, dadosBancarios: DadosBancarios, endereco: Endereco, telefone: String, celular: String, avatar: String, notificacoes: Bool, raio: Int) {
        self._id = _id
        self.contratante = contratante
        self.genero = genero
        self.tamanhoLuva = tamanhoLuva
        self.tamanhoCamiseta = tamanhoCamiseta
        self.dadosBancarios = dadosBancarios
        self.endereco = endereco
        self.telefone = telefone
        self.celular = celular
        self.avatar = avatar
        self.notificacoes = notificacoes
        self.raio = raio
    }
    
    init(_id: String?, personModel: PersonModel, contratante: String, celular: String, avatar: String, notificacoes: Bool, raio: Int ) {
        self._id = _id
        self.contratante = contratante
        self.genero = personModel.genero
        self.tamanhoLuva = personModel.tamanhoLuva
        self.tamanhoCamiseta = personModel.tamanhoCamiseta
        self.telefone = personModel.telefone
        //Atenção para o endereco
        self.endereco = Endereco(loc: personModel.endereco?.loc ?? LOC(coordinates: []), bairro: "", complemento: "", lagradouro: personModel.endereco?.lagradouro ?? "", numero: "")
        self.dadosBancarios = DadosBancarios(cpf: personModel.cpf,
                                             banco: personModel.bank,
                                             agencia: personModel.agency,
                                             conta: personModel.account)
        self.celular = celular
        self.avatar = avatar
        self.notificacoes = notificacoes
        self.raio = raio
    }
}

public class DadosBancarios: Codable {
    let cpf, banco, agencia, conta: String
    
    init(cpf: String, banco: String, agencia: String, conta: String) {
        self.cpf = cpf
        self.banco = banco
        self.agencia = agencia
        self.conta = conta
    }
}

public class NotificationPerson: Codable {
    let id: String?
    let notificacoes: Bool?
    let raio: Int?
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case notificacoes, raio
    }
    
    init(id: String?, notificacoes: Bool?, raio: Int?) {
        self.id = id
        self.notificacoes = notificacoes
        self.raio = raio
    }
}

