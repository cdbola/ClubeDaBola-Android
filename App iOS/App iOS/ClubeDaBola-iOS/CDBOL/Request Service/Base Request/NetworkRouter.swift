//
//  NetworkRouter.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 11/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

public typealias NetworkRouterCompletion = (_ data: Data?,_ response: URLResponse?,_ error: Error?)->()

protocol NetworkRouter: class {
    associatedtype EndPoint:EndPointType
    func request(_ route:EndPoint, completion: @escaping NetworkRouterCompletion)
    func cancel()
}
