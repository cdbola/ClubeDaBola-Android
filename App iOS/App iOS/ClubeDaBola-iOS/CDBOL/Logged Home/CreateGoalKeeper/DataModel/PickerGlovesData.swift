//
//  PickerGlovesData.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift


class PickerGlovesData: UIPickerView {
    public var size: [String] = ["7","8","9","10","11","12"]
    var choose = Variable<String>("7")
}

extension PickerGlovesData: UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return size.count
    }
}

extension PickerGlovesData: UIPickerViewDelegate {
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: 100, height: 50))
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 100, height: 50))
        label.text = size[row]
        label.textColor = .black
        label.textAlignment = .center
        view.addSubview(label)
        
        return view
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
        return 100
    }
    
    func pickerView( _ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        choose.value = size[row]
    }
}
