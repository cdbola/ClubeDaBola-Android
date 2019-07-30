//
//  CreatePersonRequest.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

public enum PersonAPI{
    case createGoalkeeper(goalkeeper: GoalKeeper)
    case createReferee(goalkeeper: GoalKeeper)
    case notificationGoalkeeper(notificationPerson: NotificationPerson)
    case notificationReferee(notificationPerson: NotificationPerson)
    case createWallet(contratanteId: String)
    case getGoalKeeper()
    case getReferre()
    case getRanking()
    case withdrawWallet(contratanteId: String)
    case getWallet(contratanteId: String)
    case getGoalKeeperNickname(nickname:String)
    case getReferreNickname(nickname:String)
}

extension PersonAPI:EndPointType{

    var baseUrl: URL {
        return URL(string: "https://cdbola.herokuapp.com/api/v1")!
    }
    
    
    var path: String {
        switch self {
        case .createGoalkeeper(_):
            return "/goleiros"
        case .createReferee(_):
            return "/arbitros"
        case .notificationGoalkeeper(_):
            return "/goleiros"
        case .notificationReferee(_):
            return "/arbitros"
        case .createWallet(_):
            return "/carteiras"
        case .getGoalKeeper():
            return "/goleiros"
        case .getReferre():
            return "/arbitros"
        case .getRanking():
            return "/ranking/todos"
        case .withdrawWallet(_):
            return "/resgatar/carteira"
        case .getWallet(let contratanteId):
            return "/carteira/detail/\(contratanteId)"
        case .getGoalKeeperNickname(let nickname):
            return "/goleiros/buscar/\(nickname)"
        case .getReferreNickname(let nickname):
            return "/arbitros/buscar/\(nickname)"
        }
        
    }
    
    var task: HTTPTask {
        switch self {
        case .createGoalkeeper(let goalkeeper):
             return .requestParametersAndHeader(bodyParameters: goalkeeper.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .createReferee(let referee):
            return .requestParametersAndHeader(bodyParameters: referee.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .notificationGoalkeeper(let person):
            return .requestParametersAndHeader(bodyParameters: person.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .notificationReferee(let person):
            return .requestParametersAndHeader(bodyParameters: person.dictionary, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .createWallet(let contratanteId):
            return .requestParametersAndHeader(bodyParameters: ["contratante":contratanteId], urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getGoalKeeper(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getReferre(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getRanking():
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .withdrawWallet(let contratanteId):
            return .requestParametersAndHeader(bodyParameters: ["contratante":contratanteId], urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getWallet(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getGoalKeeperNickname(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        case .getReferreNickname(_):
            return .requestParametersAndHeader(bodyParameters: nil, urlParameters: nil, additionalHeader: ["Authorization": Defaults.getUser.token!])
        }
    }
    
    var httpMethod: HTTPMethod {
        switch self {
        case .getGoalKeeper(_), .getRanking(), .getWallet(_), .getGoalKeeperNickname(_),.getReferreNickname(_) :
            return .get
        default:
            return .post

        }
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}

public struct PersonManager:EndPointManager {
    typealias T = PersonAPI
    
    public func requestNewGoalKeeper(goalkeeper:GoalKeeper,
                               successHandler: @escaping ( _ model:String) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.createGoalkeeper(goalkeeper: goalkeeper)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            switch result {
            case .success:
                successHandler("Sucesso")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func requestNewReferee(goalkeeper:GoalKeeper,
                                     successHandler: @escaping ( _ model:String) ->(),
                                     errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.createReferee(goalkeeper: goalkeeper)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)

            switch result {
            case .success:
                successHandler("Sucesso")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func requestNewWallet(contratanteId: String,
                                  successHandler: @escaping ( _ sucess: String) ->(),
                                  errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.createWallet(contratanteId: contratanteId)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getRanking(successHandler: @escaping ( _ sucess: RankingPerson) ->(),
                            errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.getRanking()) { (data, response, error) in
            let result:DecodeResult<RankingPerson> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func withdrawWallet(contratanteId: String,
                                 successHandler: @escaping ( _ sucess: String) ->(),
                                 errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.withdrawWallet(contratanteId: contratanteId)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getWallet(contratanteId: String,
                               successHandler: @escaping ( _ sucess: WalletDetail) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.getWallet(contratanteId: contratanteId)) { (data, response, error) in
            let result:DecodeResult<WalletDetail> = DecoderResponse().decodeResponser(data: data, response: response, error: error)

            switch result {
            case .success(let walletDetail):
                successHandler(walletDetail)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func getGoalKeeperNickname(nickname:String,
                                      successHandler: @escaping ( _ sucess: [Contratante]) ->(),
                                      errorHandler: @escaping ( _ error:Error) ->()){
        router.request(.getGoalKeeperNickname(nickname: nickname)) { (data, response, error) in
            let result:DecodeResult<[Contratante]> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
            
        }
    }
        
    public func getReferreNickname(nickname:String,
                                      successHandler: @escaping ( _ sucess: [Contratante]) ->(),
                                      errorHandler: @escaping ( _ error:Error) ->()){
        router.request(.getReferreNickname(nickname: nickname)) { (data, response, error) in
            let result:DecodeResult<[Contratante]> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
        
    }
    
    public func notificationGoalkeeper(notificationPerson: NotificationPerson,
                               successHandler: @escaping ( _ sucess: String) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.notificationGoalkeeper(notificationPerson: notificationPerson)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler("OK")
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func notificationReferee(notificationPerson: NotificationPerson,
                                       successHandler: @escaping ( _ sucess: String) ->(),
                                       errorHandler: @escaping ( _ error:Error) ->()) {
        
        router.request(.notificationGoalkeeper(notificationPerson: notificationPerson)) { (data, response, error) in
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

