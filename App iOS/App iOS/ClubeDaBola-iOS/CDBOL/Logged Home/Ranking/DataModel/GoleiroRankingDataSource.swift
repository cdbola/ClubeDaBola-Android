//
//  GoleiroRankingDataModel.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit

class GoleiroRankingDataSource: UITableView {
    
    fileprivate let reuseIdentifier: String = "rankCell"
    var model: [Arbitro] = []

    
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

extension GoleiroRankingDataSource: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return model.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath) as! RankingTableViewCell
        cell.configCell(model: model[indexPath.row], orderInt: (indexPath.row + 1))
        return cell
    }
    
}

extension GoleiroRankingDataSource: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 60
    }
}
