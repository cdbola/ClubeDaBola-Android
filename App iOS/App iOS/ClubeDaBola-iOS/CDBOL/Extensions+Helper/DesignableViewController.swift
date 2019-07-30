//
//  DesignableViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 13/09/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

@IBDesignable
class DesignableViewController: UIViewController {
    
    @IBInspectable var LightStatusBar: Bool = false
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        get {
            if LightStatusBar {
                return UIStatusBarStyle.lightContent
            } else {
                return UIStatusBarStyle.default
            }
        }
    }
}
