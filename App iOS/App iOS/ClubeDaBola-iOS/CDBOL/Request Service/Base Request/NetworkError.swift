//
//  NetworkError.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 11/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

public enum NetworkError : String, Error, Equatable {

    case parametersNil = "Parameters are nil"
    case encodingFailed = "Encode Fail"
    case missingURL = "Missing URL"

    public static func == (lhs: NetworkError, rhs: NetworkError) -> Bool {
        return lhs.rawValue == rhs.rawValue
    }

}
