//
//  BasicResponse.swift
//  CDBOL
//
//  Created by Paulo Rosa on 17/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class BasicResponse: Codable {
    let sucesso: Bool
    let mensagem: String
    
    init(sucesso: Bool, mensagem: String) {
        self.sucesso = sucesso
        self.mensagem = mensagem
    }
}
