//
//  MatchRequest.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

public enum MatchAPI{
    case createMatch(match:NewMatch)
    case requestMatchs(email:String)
    case getMatchs(contratanteId: String)
    case deleteMatchs(contratanteId: String)
    case getHiredMatchs(contratanteId: String)
    case getPastMatchs(contratanteId: String)
    case getHiredPastMatchs(contratanteId: String)
    case acceptMatch(partidaId: String, contratanteId: String)
    case refuseMatch(partidaId: String)
    case rateMatch(rate: Rate)
}

extension MatchAPI:EndPointType{
    var baseUrl: URL {
        return URL(string: "https://cdbola.herokuapp.com/api/v1")!
    }
    
    var path: String {
        switch self {
        case .requestMatchs(_):
            return "Partida"
        case .getMatchs(let contratanteId):
            return "/partidas/recentes/\(contratanteId)"
        case .deleteMatchs(let contratanteId):
            return "/partidas/\(contratanteId)"
        case .getHiredMatchs(let contratanteId):
            return "/partidas/recentes/contratado/\(contratanteId)"
        case .getPastMatchs(let contratanteId):
            return "/partidas/passadas/\(contratanteId)"
        case .getHiredPastMatchs(let contratanteId):
            return "/partidas/passadas/contratado/\(contratanteId)"
        case .acceptMatch(_,_):
            return "/partidas/vincular"
        case .refuseMatch(let partidaId):
            return "/partidas/\(partidaId)/desvincular"
        case .rateMatch(_):
            return "/partidas/avaliar"
        case .createMatch(_):
            return "/partidas"
        }
    }
    
    var task: HTTPTask {
        switch self {
        case .requestMatchs(let email):
            return .requestParameters(bodyParameters: nil, urlParameters: ["email":email])
        case .getMatchs(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": UserSingleton.shared.Token ?? Defaults.getUser.token!])
        case .deleteMatchs(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getHiredMatchs(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getPastMatchs(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getHiredPastMatchs(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .acceptMatch(let partida, let contratante):
            return .requestParametersAndHeader(bodyParameters: ["partida":partida,
                                                                "contratante":contratante],
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .refuseMatch(_):
            return .requestParametersAndHeader(bodyParameters: nil,
                                               urlParameters: nil,
                                               additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .rateMatch(let rate):
            return .requestParametersAndHeader(bodyParameters: rate.dictionary,
                                              urlParameters: nil,
                                              additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .createMatch(let match):
            return .requestParametersAndHeader(bodyParameters: match.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        }
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        case .requestMatchs(_), .getHiredMatchs(_), .getMatchs(_), .getPastMatchs(_), .getHiredPastMatchs(_):
            return .get
        case .deleteMatchs(_), .refuseMatch(_):
            return .delete
        case .acceptMatch(_,_):
            return .post
        case .rateMatch(_):
            return .put
        case .createMatch(_):
            return .post
        }
    }
    
    var headers: HTTPHeaders? {
        return nil
    }

}

public struct MatchManager:EndPointManager{
    typealias T = MatchAPI
    
    public func requestMatchs(email:String,
                             successHandler: @escaping ( _ model:[Match]) ->(),
                             errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.requestMatchs(email: email)) { (data, response, error) in
            let result:DecodeResult<[Match]> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getMatchs(contratanteId:String,
                              successHandler: @escaping ( _ model:RecentMatch) ->(),
                              errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.getMatchs(contratanteId: contratanteId)) { (data, response, error) in
            
            let result:DecodeResult<RecentMatch> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }

    public func deleteMatchs(contratanteId: String,
                                 successHandler: @escaping ( _ sucess: String) ->(),
                                 errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.deleteMatchs(contratanteId: contratanteId)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getHiredMatchs(contratanteId:String,
                          successHandler: @escaping ( _ model:HiredMatch) ->(),
                          errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.getHiredMatchs(contratanteId: contratanteId)) { (data, response, error) in
            let result:DecodeResult<HiredMatch> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getPastMatchs(contratanteId:String,
                               successHandler: @escaping ( _ model:[HistoricMatch]) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.getPastMatchs(contratanteId: contratanteId)) { (data, response, error) in
            let result:DecodeResult<[HistoricMatch]> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getHiredPastMatchs(contratanteId:String,
                                   successHandler: @escaping ( _ model: [HistoricMatch]) ->(),
                              errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.getHiredPastMatchs(contratanteId: contratanteId)) { (data, response, error) in
            let result:DecodeResult<[HistoricMatch]> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func acceptMatch(partidaId: String, contratanteId:String,
                                   successHandler: @escaping ( _ model:String) ->(),
                                   errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.acceptMatch(partidaId: partidaId, contratanteId:contratanteId)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func refuseMatch(partidaId: String,
                             successHandler: @escaping ( _ sucess: String) ->(),
                             errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.refuseMatch(partidaId: partidaId)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func rateMatch(rate: Rate,
                            successHandler: @escaping ( _ sucess: String) ->(),
                            errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.rateMatch(rate: rate)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    
    public func createMatch(match:NewMatch,
                            successHandler: @escaping ( _ sucess: String) ->(),
                            errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.createMatch(match:match)) { (data, response, error) in
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



