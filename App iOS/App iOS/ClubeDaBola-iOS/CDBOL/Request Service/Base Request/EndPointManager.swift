//
//  EndPointManager.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 15/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

protocol EndPointManager {
    associatedtype T:EndPointType
    var router:Router<T> { get }    
}

extension EndPointManager {
    var router:Router<Self.T> {
        return Router<Self.T>()
    }
}
