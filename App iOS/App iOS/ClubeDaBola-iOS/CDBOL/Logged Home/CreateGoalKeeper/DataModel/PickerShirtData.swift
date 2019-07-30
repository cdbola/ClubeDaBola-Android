//
//  PickerGenderData.swift
//  CDBOL
//
//  Created by Paulo Rosa on 25/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift


class PickerShirtData: UIPickerView {
    public var size: [String] = ["P","M","G","GG"]
    var choose = Variable<String>("P")
}

extension PickerShirtData: UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return size.count
    }
}

extension PickerShirtData: UIPickerViewDelegate {
    
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
