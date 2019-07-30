//
//  ParameterEncode.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 11/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

public protocol ParameterEncoder {
    static func encode(urlRequest: inout URLRequest, with parameters: Parameters) throws
}

public struct URLParameterEncoder: ParameterEncoder {
    public static func encode(urlRequest: inout URLRequest, with parameters: Parameters) throws {
        guard let url = urlRequest.url else { throw NetworkError.missingURL }
        if var  urlComponentes  = URLComponents(url: url, resolvingAgainstBaseURL: true), !parameters.isEmpty {
            urlComponentes.queryItems = [URLQueryItem]()
            for (key, value) in parameters {
                let querryItem = URLQueryItem(name: key, value: String(describing: value))
                urlComponentes.queryItems?.append(querryItem)
            }
            urlRequest.url = urlComponentes.url
            
        }
        if urlRequest.value(forHTTPHeaderField: "Content-Type") == nil {
            urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")

        }
    }
}

public struct JSONParameterEncoder:ParameterEncoder {
    public static func encode(urlRequest: inout URLRequest, with parameters: Parameters) throws {
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)
            urlRequest.httpBody = jsonData
            if urlRequest.value(forHTTPHeaderField: "Content-Type") == nil {
               urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            }
        } catch { throw NetworkError.encodingFailed }
    }
}
