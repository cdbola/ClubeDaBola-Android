//
//  CustomersRequest.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 18/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit

public enum CustomerAPI {
    case setCustomer(costumer: Customer)
    case getCustomer(costumerId: String)
    case setCreditCard(customerCreditCard: CustomerNewCard)
}

extension CustomerAPI:EndPointType {
    
    var baseUrl: URL {
        return URL(string: "https://cdbola.herokuapp.com/api/v1")!
    }
    
    
    var path: String {
        switch self {
        case .setCustomer(_):
            return "/customers"
        case .getCustomer(let costumerId):
            return "/customers/\(costumerId)"
        case .setCreditCard(_):
            return "/creditCards"
        }
        
    }
    
    var task: HTTPTask {
        switch self {
            case .setCustomer(let customer):
                return .requestParametersAndHeader(bodyParameters: customer.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
            case .getCustomer(_):
                return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .setCreditCard(let customerCreditCard):
                return .requestParametersAndHeader(bodyParameters: customerCreditCard.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        }
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        case .setCustomer(_):
            return .post
        case .getCustomer(_):
            return .get
        case .setCreditCard(_):
            return .post
        }
       
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}

public struct CustomerManager:EndPointManager {
    typealias T = CustomerAPI
    
    public func setCustomer(customer: Customer,
                         successHandler: @escaping ( _ model:String) ->(),
                         errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.setCustomer(costumer: customer)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            print(String(data: data!, encoding: .utf8))
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }

    public func getCustomer(customerId: String,
                            successHandler: @escaping ( _ model:Customer) ->(),
                            errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.getCustomer(costumerId: customerId)) { (data, response, error) in
            let result:DecodeResult<Customer> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func setCreditCard(customerCreditCard: CustomerNewCard,
                            successHandler: @escaping ( _ model:String) ->(),
                            errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.setCreditCard(customerCreditCard: customerCreditCard)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)

            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
}
