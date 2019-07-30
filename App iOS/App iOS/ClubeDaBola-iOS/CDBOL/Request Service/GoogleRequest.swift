//
//  GoogleRequest.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

enum GoogleAPI {
    case autocomplete(input:String)
    case findPlace(input:String)
}
//https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=playball%20ipiranga&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&key=AIzaSyCGwbwpjyrSpOwhqXrm5Wjl1a5bnKo08Mg
extension GoogleAPI:EndPointType {
    var baseUrl: URL {
        guard let url = URL(string: "https://maps.googleapis.com/maps/api/") else {
            fatalError()
        }
        return url
    }
    
    var path: String {
        switch self {
        case .autocomplete(_):
            return "geocode/json"
        case .findPlace( _ ):
            return "place/findplacefromtext/json"
        
        }
    }
    
    var task: HTTPTask {
        switch self {
        case .autocomplete(let input):
            return .requestParameters(bodyParameters: nil, urlParameters: ["address":input,
                                                                           "key":"AIzaSyCGwbwpjyrSpOwhqXrm5Wjl1a5bnKo08Mg"])
        case .findPlace(let input):
            return .requestParameters(bodyParameters: nil, urlParameters: ["input":input,
                                                                           "inputtype":"textquery",
                                                                           "fields":"photos,formatted_address,name,rating,opening_hours,geometry",
                                                                        "key":"AIzaSyCGwbwpjyrSpOwhqXrm5Wjl1a5bnKo08Mg"])
        }
    }
    
    var httpMethod: HTTPMethod {
        return .get
    }
    
    var headers: HTTPHeaders? {
        return nil
    }
}

struct GoogleAPImanager:EndPointManager {
    typealias T = GoogleAPI

    
    private func parseAutoCompleteCadidate(data:Data) -> GoogleAutocomplete {
        do {
            let model = try JSONDecoder().decode(GoogleAutocomplete.self, from: data)
            return model
        }catch {
            return GoogleAutocomplete()
        }
    }
    
    
    func getSuggestionPlace(with  input:String, types:String = "geocode",
                       language:String = "pt-BR",
                       completionHandler: @escaping (_ suggestions: GoogleAutocomplete?,_ error: Error?) ->()) {
        router.request(.findPlace(input: input)) { (data, response, error) in
            
            if let _ = error {
                completionHandler(nil,NetworkResponse.noConnection)
                return
            }
            
            guard let response = response as? HTTPURLResponse else {
                completionHandler(nil,NetworkResponse.noData)
                return
            }
            
            let result = NetworkResponseManager.handleNetworkResponse(response)
            
            switch result {
            case .success:
                guard let responseData = data else {
                    //No Data
                    completionHandler(nil,NetworkResponse.unableToDecode)
                    return
                }
                let suggestions = self.parseAutoCompleteCadidate(data: responseData)
                completionHandler(suggestions,nil)
                return
                
            case .failure(let netWorkError):
                completionHandler(nil,netWorkError)
                return
            }
        }
    }
}
