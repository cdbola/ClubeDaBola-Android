//
//  EarningsTableViewCell.swift
//  CDBOL
//
//  Created by Paulo Rosa on 28/11/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import Foundation

class EarningsTableViewCell: UITableViewCell {

    @IBOutlet weak var day: UILabel!
    @IBOutlet weak var total: UILabel!


    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configureCell(extrato:Extrato) {
        self.day.text = dateChangeMask(date:extrato.dataAdicao ?? "00-00-0000")
        if let valor = extrato.valor {
            self.total.text = formatValor(valor: valor).format(numberStyle: .currency, locale: "pt_BR".toLocale)
        }
    }
    
    func dateChangeMask(date: String)  -> String {
        let arrayDate = date.components(separatedBy: "-")
        let dateFinal = "\(arrayDate[0])/\(arrayDate[1])/\(arrayDate[2])"
        return dateFinal
    }
    
    func formatValor(valor: Int) -> Double {
        if valor < 10 {
            return Double(valor)
        }
        let valorString = "\(valor)"
        let count = valorString.count - 2
        let prefix = String(valorString.prefix(count))
        let sufix = String(valorString.suffix(2))
        
        let novoValor = "\(prefix).\(sufix)"
        return Double(novoValor) ?? 0.0
        
    }

}

extension String {
    var toLocale: Locale {
        return Locale(identifier: self)
    }
}

extension Numeric {
    
    func format(numberStyle: NumberFormatter.Style = NumberFormatter.Style.decimal, locale: Locale = Locale.current) -> String? {
        if let num = self as? NSNumber {
            let formater = NumberFormatter()
            formater.numberStyle = numberStyle
            formater.locale = locale
            return formater.string(from: num)
        }
        return nil
    }
    
    func format(numberStyle: NumberFormatter.Style = NumberFormatter.Style.decimal, groupingSeparator: String = ".", decimalSeparator: String = ",") -> String? {
        if let num = self as? NSNumber {
            let formater = NumberFormatter()
            formater.numberStyle = numberStyle
            formater.groupingSeparator = groupingSeparator
            formater.decimalSeparator = decimalSeparator
            return formater.string(from: num)
        }
        return nil
    }
}
