//
//  PickerBankData.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift


class PickerBankData: UIPickerView {
    public var banks: [Bank] = []
    var choose = Variable<Bank>(Bank())
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.commonInit()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.commonInit()
    }
    
    private func commonInit() {
        loadJSON()
    }

    func loadJSON() {
        if let path = Bundle.main.path(forResource: "bankList", ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                let result = try JSONDecoder().decode([Bank].self, from: data)
                self.banks = result
            } catch {
                // handle error
            }
        }
    }
}

extension PickerBankData: UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return banks.count
    }
}

extension PickerBankData: UIPickerViewDelegate {
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        let view = UIView(frame: CGRect(x: 0, y: 0, width: 300, height: 50))
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 50))
        label.text = "\(banks[row].code) - \(banks[row].name)"
        label.font = label.font.withSize(12)
        label.textColor = .black
        label.textAlignment = .center
        view.addSubview(label)
        
        return view
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
        return 100
    }
    
    func pickerView( _ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        choose.value = banks[row]
    }
}
