//
//  HTTPTask.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 11/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

public typealias HTTPHeaders = [String:String]
public typealias Parameters = [String:Any]

public enum HTTPTask {
    case request
    
    case requestParameters(bodyParameters:Parameters?,
                           urlParameters:Parameters?)
    
    case requestParametersAndHeader(bodyParameters:Parameters?,
                                    urlParameters:Parameters?,
                                    additionalHeader:HTTPHeaders?)
}
