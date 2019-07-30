//
//  EndPointType.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 11/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

public typealias CompletionHandler = (_ data: Data?,_ response: URLResponse?,_ error: Error?)->()

protocol EndPointType {
    var baseUrl:URL { get }
    var path:String { get }
    var task:HTTPTask { get }
    var httpMethod:HTTPMethod { get }
    var headers:HTTPHeaders? { get }
}

