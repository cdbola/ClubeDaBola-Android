//
//  NewUser.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

public class NewUser: Codable {
    let nome, apelido, imagem, dataNascimento: String
    let email, senha: String
    
    init(nome: String, apelido: String, imagem: String, dataNascimento: String, email: String, senha: String) {
        self.nome = nome
        self.apelido = apelido
        self.imagem = imagem
        self.dataNascimento = dataNascimento
        self.email = email
        self.senha = senha
    }
    
    init(nome: String, apelido: String, imagem: String, dataNascimento: String, email: String) {
        self.nome = nome
        self.apelido = apelido
        self.imagem = imagem
        self.dataNascimento = dataNascimento
        self.email = email
        self.senha = ""
    }
    init(){
        self.nome = ""
        self.apelido = ""
        self.imagem = ""
        self.dataNascimento = ""
        self.email = ""
        self.senha = ""
    }
}

