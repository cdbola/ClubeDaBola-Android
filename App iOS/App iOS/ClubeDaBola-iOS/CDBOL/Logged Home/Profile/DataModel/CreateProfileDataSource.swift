//
//  CreateProfileDataSource.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift

class CreateProfileDataSource: UITableView {
    
    fileprivate let reuseIdentifier: String = "profileCell"
    var options: [String] = ["Formas de pagamento",
                                         "Contar a um amigo",
                                         "Quero virar jogador",
                                         "Ajuda", "Sair"]
    var choose = Variable(0)
    
    override init(frame: CGRect, style: UITableViewStyle) {
        super.init(frame: frame, style: style)
        self.commonInit()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.commonInit()
    }
    
    private func commonInit() {
    }

}

extension CreateProfileDataSource: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return options.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath) as! ProfileTableViewCell
        cell.title.text = options[indexPath.row]
        return cell
    }
    
}

extension CreateProfileDataSource: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 60
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        choose.value = indexPath.row
    }
    
}
