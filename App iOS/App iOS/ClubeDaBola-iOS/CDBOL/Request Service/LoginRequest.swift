//
//  LoginViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import Firebase
import FirebaseInstanceID
import FirebaseMessaging


public enum LoginAPI{
    case login(email:String, password:String)
    case cadastro(user: NewUser)
    case loginFacebook(user:NewUser)
    case token(user:UserSingleton)
}


extension LoginAPI:EndPointType{
    var baseUrl: URL {
        return URL(string: "https://cdbola.herokuapp.com/api/v1")!
    }
    
    var path: String {
        switch self {
        case .login(_, _):
            return "contratantes/auth/login"
        case .cadastro(_):
            return "contratantes/auth/signup"
        case .token(_):
            return "notificacoes"
        case .loginFacebook(_):
            return "contratantes/auth/login/facebook"
        }
        
    }
    
    var task: HTTPTask {
        switch self {
        case .login(let email, let password):
            return .requestParameters(bodyParameters: ["email":email,
                                                       "senha":password], urlParameters: nil)
            
        case .cadastro(let user):
            return .requestParameters(bodyParameters:
                ["nome":user.nome,
                    "apelido":user.apelido,
                    "imagem":user.nome,
                    "dataNascimento":user.dataNascimento,
                    "email":user.email,
                    "senha":user.senha], urlParameters: nil)
        case .token(let user):
            return .requestParametersAndHeader(bodyParameters: ["contratante":user.Id ?? Defaults.getUser._id!,
                                                                "tokenNotification":Messaging.messaging().fcmToken ?? ""], urlParameters: nil, additionalHeader: ["Authorization": user.Token ?? Defaults.getUser.token!])
        case .loginFacebook(let user):
            return .requestParameters(bodyParameters: ["nome":user.nome,
                                                       "apelido":user.apelido,
                                                       "imagem":user.nome,
                                                       "dataNascimento":user.dataNascimento,
                                                       "email":user.email], urlParameters: nil)
        }
    }
    
    var httpMethod: HTTPMethod {
        return .post
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}

public struct LoginManagerLocal:EndPointManager{
    typealias T = LoginAPI
    
    public func requestLogin(email:String, password:String,
                             successHandler: @escaping ( _ model:User) ->(),
                             errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.login(email: email, password: password)) { (data, response, error) in
            let result:DecodeResult<User> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func requestNewUser(user:NewUser,
                               successHandler: @escaping ( _ model:BasicResponse) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.cadastro(user: user)) { (data, response, error) in
            let result:DecodeResult<BasicResponse> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
    public func requestPostNotification(user:UserSingleton,
                                        successHandler: @escaping ( _ success:Bool) ->(),
                                        errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.token(user: user)) { (data, response, error) in
            let result = NetworkResponseManager.handleNetworkResponse(response as! HTTPURLResponse)
            
            switch result {
            case .success:
                successHandler(true)
            case .failure(let error):
                errorHandler(error)
            }
        }
        
    }
    
    public func requestNewUserFacebook(user:NewUser,
                               successHandler: @escaping ( _ model:User) ->(),
                               errorHandler: @escaping ( _ error:Error) ->()){
        
        router.request(.loginFacebook(user: user)) { (data, response, error) in
            let result:DecodeResult<User> = DecoderResponse().decodeResponser(data: data, response: response, error: error)
            
            switch result {
            case .success(let model):
                successHandler(model)
            case .failure(let error):
                errorHandler(error)
            }
        }
    }
    
}
