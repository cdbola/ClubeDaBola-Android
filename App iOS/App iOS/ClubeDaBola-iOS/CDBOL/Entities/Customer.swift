//
//  Costumer.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 18/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation

public class Customer: Codable {
    let ownID, fullname, email, birthDate: String
    let taxDocument: TaxDocument
    let phone: Phone
    let shippingAddress: IngAddress
    let fundingInstrument: FundingInstrument
    
    enum CodingKeys: String, CodingKey {
        case ownID = "ownId"
        case fullname, email, birthDate, taxDocument, phone, shippingAddress, fundingInstrument
    }
    
    init(ownID: String, fullname: String, email: String, birthDate: String, taxDocument: TaxDocument, phone: Phone, shippingAddress: IngAddress, fundingInstrument: FundingInstrument) {
        self.ownID = ownID
        self.fullname = fullname
        self.email = email
        self.birthDate = birthDate
        self.taxDocument = taxDocument
        self.phone = phone
        self.shippingAddress = shippingAddress
        self.fundingInstrument = fundingInstrument
    }
}

class FundingInstrument: Codable {
    let method: String
    let creditCard: CreditCard
    
    init(method: String, creditCard: CreditCard) {
        self.method = method
        self.creditCard = creditCard
    }
}

class CreditCard: Codable {
    let expirationMonth, expirationYear, number, cvc: String
    let holder: Holder
    
    init(expirationMonth: String, expirationYear: String, number: String, cvc: String, holder: Holder) {
        self.expirationMonth = expirationMonth
        self.expirationYear = expirationYear
        self.number = number
        self.cvc = cvc
        self.holder = holder
    }
}

class Holder: Codable {
    let fullname, birthdate: String
    let taxDocument: TaxDocument
    let billingAddress: IngAddress?
    let phone: Phone
    
    init(fullname: String, birthdate: String, taxDocument: TaxDocument, billingAddress: IngAddress?, phone: Phone) {
        self.fullname = fullname
        self.birthdate = birthdate
        self.taxDocument = taxDocument
        self.billingAddress = billingAddress
        self.phone = phone
    }
}

class IngAddress: Codable {
    let city, district, street, streetNumber: String
    let zipCode, state, country: String
    let complement: String?
    
    init(city: String, district: String, street: String, streetNumber: String, zipCode: String, state: String, country: String, complement: String?) {
        self.city = city
        self.district = district
        self.street = street
        self.streetNumber = streetNumber
        self.zipCode = zipCode
        self.state = state
        self.country = country
        self.complement = complement
    }
}

class Phone: Codable {
    let countryCode, areaCode, number: String
    
    init(countryCode: String, areaCode: String, number: String) {
        self.countryCode = countryCode
        self.areaCode = areaCode
        self.number = number
    }
}

class TaxDocument: Codable {
    let type, number: String
    
    init(type: String, number: String) {
        self.type = type
        self.number = number
    }
}

public class CustomerNewCard: Codable {
    let customerID: String
    let creditCard: CreditCard
    
    enum CodingKeys: String, CodingKey {
        case customerID = "customerId"
        case creditCard
    }
    
    init(customerID: String, creditCard: CreditCard) {
        self.customerID = customerID
        self.creditCard = creditCard
    }
}
