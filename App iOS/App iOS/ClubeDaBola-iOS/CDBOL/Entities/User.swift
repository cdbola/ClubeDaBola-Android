//
//  User.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

// To parse the JSON, add this file to your project and do:
//
//   let user = try? newJSONDecoder().decode(User.self, from: jsonData)

import Foundation

public class User: Codable {
    let contratante: Contratante
    let token: String
    let success: Bool
    let message: String
    
    init(contratante: Contratante, token: String, success: Bool, message: String) {
        self.contratante = contratante
        self.token = token
        self.success = success
        self.message = message
    }
}

public class Contratante: Codable {
    let id, nome, apelido, dataNascimento: String
    let email, created: String
    let avatar: String?
    let customerId: String?
    let arbitro, goleiro: GoalKeeper?

    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case nome, apelido, dataNascimento, email, customerId, arbitro, goleiro, avatar
        case created = "_created"
    }
    
    init(id: String, nome: String, apelido: String, dataNascimento: String, email: String, created: String, customerId: String,
         arbitro: GoalKeeper, goleiro: GoalKeeper, avatar: String) {
        self.id = id
        self.nome = nome
        self.apelido = apelido
        self.dataNascimento = dataNascimento
        self.email = email
        self.created = created
        self.customerId = customerId
        self.goleiro = goleiro
        self.arbitro = arbitro
        self.avatar = avatar
    }
}

struct Defaults {
    
    static let (tokenKey, idKey) = ("token", "id")
    static let userSessionKey = "com.save.usersession"
    static let sessionCustomer = "com.save.customer"
    
    struct UserModel {
        var token: String?
        var _id: String?
        
        init(_ json: [String: String]) {
            self.token = json[tokenKey]
            self._id = json[idKey]
        }
    }
    
    static var saveUser = { (token: String, _id: String) in
        UserDefaults.standard.set([tokenKey: token, idKey: _id], forKey: userSessionKey)
    }
    
    static var getUser = { _ -> UserModel in
        return UserModel((UserDefaults.standard.value(forKey: userSessionKey) as? [String: String]) ?? [UserSingleton.shared.Id!:UserSingleton.shared.Token!])
    }(())
    
    static var getUserForLogin = { _ -> UserModel in
        return UserModel((UserDefaults.standard.value(forKey: userSessionKey) as? [String: String]) ?? [:])
    }(())
    
    static func clearUserData(){
        UserDefaults.standard.removeObject(forKey: userSessionKey)
    }
    
    static var saveCustomerId = { (customerId: String) in
        UserDefaults.standard.set(customerId, forKey: sessionCustomer)
    }
    
    static var getCustomerId = { _ -> String in
        return UserDefaults.standard.value(forKey: sessionCustomer) as? String ?? ""
    }(())
    
    static func clearCustomerId(){
        UserDefaults.standard.removeObject(forKey: sessionCustomer)
    }
    
    static func clearAll() {
        UserDefaults.standard.removeObject(forKey: userSessionKey)
        UserDefaults.standard.removeObject(forKey: sessionCustomer)
        if let bundle = Bundle.main.bundleIdentifier {
            UserDefaults.standard.removePersistentDomain(forName: bundle)
        }
    }
    
}


public class UserSingleton{
    static let shared = UserSingleton()
    
    var Id: String?
    var NomeCompleto: String?
    var DescricaoTipoCadastro: String?
    var Email: String?
    var Apelido: String?
    var Token: String?
    
    private init() {}

}
