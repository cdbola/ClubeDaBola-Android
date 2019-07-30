//
//  Bank.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

class Bank: Codable {
    let idBank: Int
    let code, name, createdAt, updatedAt: String
    let deletedAt: String
    let isDeleted: Bool
    
    enum CodingKeys: String, CodingKey {
        case idBank = "IdBank"
        case code = "Code"
        case name = "Name"
        case createdAt = "CreatedAt"
        case updatedAt = "UpdatedAt"
        case deletedAt = "DeletedAt"
        case isDeleted = "IsDeleted"
    }
    
    init(idBank: Int, code: String, name: String, createdAt: String, updatedAt: String, deletedAt: String, isDeleted: Bool) {
        self.idBank = idBank
        self.code = code
        self.name = name
        self.createdAt = createdAt
        self.updatedAt = updatedAt
        self.deletedAt = deletedAt
        self.isDeleted = isDeleted
    }
    init() {
        self.idBank = 0
        self.code = ""
        self.name = ""
        self.createdAt = ""
        self.updatedAt = ""
        self.deletedAt = ""
        self.isDeleted = false
    }
}

