//
//  HirerRequest.swift
//  CDBOL
//
//  Created by Paulo Rosa on 14/12/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

public enum HirerAPI {
    case getHirer(contratanteId: String)
    case putHirer(hirer: Hirer)
    case resetPass(contratanteId: String, newPassword: String)
}

extension HirerAPI:EndPointType {
    
    var baseUrl: URL {
        return URL(string: "https://cdbola.herokuapp.com/api/v1")!
    }
    
    
    var path: String {
        switch self {
        case .getHirer(let contratanteId):
            return "/contratantes/\(contratanteId)"
        case .putHirer(_):
            return "/contratantes"
        case .resetPass(_,_):
            return "/contratantes/auth/resetar"
        }
        
        
    }
    
    var task: HTTPTask {
        switch self {
        case .getHirer(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case.putHirer(let hirer):
            return .requestParametersAndHeader(bodyParameters: hirer.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case.resetPass(let contratanteId, let password):
            return .requestParametersAndHeader(bodyParameters: ["contratante":contratanteId,"senha": password], urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])

        }
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        case .putHirer(_):
            return .put
        case .getHirer(_):
            return .get
        case .resetPass(_,_):
            return .post
        }
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}

public struct HirerManager:EndPointManager {
    typealias T = HirerAPI
    
    public func getHirer(contratanteId: String,
                                     successHandler: @escaping ( _ model:Contratante) ->(),
                                     errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.getHirer(contratanteId: contratanteId)) { (data, response, error) in
            let result:DecodeResult<Contratante> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func putHirer(hirer: Hirer,
                         successHandler: @escaping ( _ model:Hirer) ->(),
                         errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.putHirer(hirer: hirer)) { (data, response, error) in
            let result:DecodeResult<Hirer> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func resetPass(contratanteId: String,
                          password: String,
                         successHandler: @escaping ( _ model:String) ->(),
                         errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.resetPass(contratanteId: contratanteId, newPassword: password)) { (data, response, error) in
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


