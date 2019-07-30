//
//  PaymentsViewModel.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 18/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

class PaymentsViewModel {
    
    func validateCard(holder: Holder, validate: String, number: String, cvc: String) -> CreditCard {
        let cardArray = expirationSplit(validate: validate)
        let cardNumber = creditCardNoMask(cardNumber: number)
        let creditCard = CreditCard(expirationMonth: cardArray[0],
                                    expirationYear: cardArray[1],
                                    number: cardNumber,
                                    cvc: cvc,
                                    holder: holder)
        
        return creditCard
    }
    
    func expirationSplit(validate: String) -> [String] {
        let arrayString = validate.components(separatedBy: "/")
        return arrayString
    }
    
    func phoneSplit(validate: String) -> [String] {
        var stringRemove = validate.replacingOccurrences(of: "(", with: "")
        stringRemove = stringRemove.replacingOccurrences(of: ")", with: "-")
        var arrayString = stringRemove.components(separatedBy: "-")
        arrayString[1] = arrayString[1].replacingOccurrences(of: " ", with: "")
        return arrayString
    }
    
    func creditCardNoMask(cardNumber: String) -> String {
        return cardNumber.replacingOccurrences(of: " ", with: "")
    }
    
    func cpfNoMask(cpf: String) -> String {
        var cpfFinal = cpf.replacingOccurrences(of: ".", with: "")
        cpfFinal = cpfFinal.replacingOccurrences(of: "-", with: "")
        return cpfFinal
    }
    
    func cepNoMask(cep: String) -> String {
        return cep.replacingOccurrences(of: "-", with: "")
    }
    
    func dateChangeMask(date: String)  -> String {
        let arrayDate = date.components(separatedBy: "/")
        let dateFinal = "\(arrayDate[2])-\(arrayDate[1])-\(arrayDate[0])"
        return dateFinal
    }
    
}
